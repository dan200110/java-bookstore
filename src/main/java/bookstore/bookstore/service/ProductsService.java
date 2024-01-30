package bookstore.bookstore.service;

import bookstore.bookstore.dto.ProductsPaginationDTO;
import bookstore.bookstore.dto.ProductsDTO;
import bookstore.bookstore.model.CategoryEntity;
import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.ProductsRepository;
import bookstore.bookstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.form.OptionsTag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService implements ProductsServiceInterface{
    private final ProductsRepository productsRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final CategoryService categoryService;

    @Override
    public ProductsPaginationDTO getAllProductsPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ProductsEntity> productsEntities = productsRepository.findAll(pageable);

        List<ProductsDTO> productsDTOList = productsEntities.stream().map(product -> mapToDTO(product)).toList();

        ProductsPaginationDTO productsPaginationDTO = mapToPagination(productsEntities);
        productsPaginationDTO.setProductsDTOList(productsDTOList);

        return productsPaginationDTO;
    }

    @Override
    public Optional<List<ProductsDTO>> findAllProducts() {
        List<ProductsEntity> productsEntities = productsRepository.findAll()/* Fetch products from repository */;
        List<ProductsDTO> productsDTOList = productsEntities.stream().map(this::mapToDTO).collect(Collectors.toList());
        return Optional.of(productsDTOList);
    }

    @Override
    public Optional<ProductsEntity> findProductById(int id) {
        return productsRepository.findById(id);
    }

    @Override
    public ProductsPaginationDTO getProductsByName(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ProductsEntity> productsEntities = productsRepository.searchProducts(query, pageable);
        List<ProductsDTO> productsDTOList = productsEntities.stream().map(product -> mapToDTO(product)).toList();

        ProductsPaginationDTO productsPaginationDTO = mapToPagination(productsEntities);
        productsPaginationDTO.setProductsDTOList(productsDTOList);

        return productsPaginationDTO;
    }

    @Override
    public Boolean createProduct(String jwtToken, ProductsDTO productsDTO) {
        Optional<UsersEntity> usersEntity = userService.getUserByUserName(jwtUtils.getUserNameFromJwtToken(jwtToken));
        if (usersEntity.isPresent()) {
            CategoryEntity categoryEntity = getCategoryEntity(productsDTO);

            ProductsEntity productsEntity = mapToEntity(productsDTO);
            productsEntity.setUsersEntity(usersEntity.get());
            productsEntity.setCategoryEntity(categoryEntity);

            try {
                productsRepository.save(productsEntity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Boolean deleteProductById(Integer productId) {
        try {
            productsRepository.deleteById(productId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean updateProductById(String jwtToken, int productId, ProductsDTO updateProduct) {
        Optional<ProductsEntity> productsEntity = productsRepository.findById(productId);
        Optional<UsersEntity> usersEntity = userService.getUserByUserName(jwtUtils.getUserNameFromJwtToken(jwtToken));

        if (productsEntity.isPresent()) {
            CategoryEntity categoryEntity = getCategoryEntity(updateProduct);
            if (isValidProduct(updateProduct) && productsEntity.get().getUsersEntity().getId() == usersEntity.get().getId()) {
                updateProductFields(productsEntity.get(), updateProduct, categoryEntity);
                try {
                    productsRepository.save(productsEntity.get());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private CategoryEntity getCategoryEntity(ProductsDTO productsDTO) {
        return categoryService.findCategoryEntityByCategoryName(productsDTO.getCategoryName())
                .orElseGet(() -> {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setCategoryName(productsDTO.getCategoryName());
                    return categoryEntity;
                });
    }

    private boolean isValidProduct(ProductsDTO productsDTO) {
        return productsDTO.getQuantity() != null
                && productsDTO.getProductDetails() != null
                && productsDTO.getProductPhoto() != null
                && productsDTO.getProductName() != null
                && productsDTO.getProductPrice() != null;
    }

    private void updateProductFields(ProductsEntity productsEntity, ProductsDTO productsDTO, CategoryEntity categoryEntity) {
        productsEntity.setQuantity(productsDTO.getQuantity());
        productsEntity.setProductDetails(productsDTO.getProductDetails());
        productsEntity.setProductPhoto(productsDTO.getProductPhoto());
        productsEntity.setProductName(productsDTO.getProductName());
        productsEntity.setProductPrice(productsDTO.getProductPrice());
        productsEntity.setCategoryEntity(categoryEntity);
    }

    private ProductsDTO mapToDTO(ProductsEntity productsEntity) {
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProductId(productsEntity.getId());
        productsDTO.setProductName(productsEntity.getProductName());
        productsDTO.setProductPrice(productsEntity.getProductPrice());
        productsDTO.setQuantity(productsEntity.getQuantity());
        productsDTO.setProductPhoto(productsEntity.getProductPhoto());
        productsDTO.setProductDetails(productsEntity.getProductDetails());
        productsDTO.setProviderId(productsEntity.getUsersEntity().getId());
        productsDTO.setCategoryName(productsEntity.getCategoryEntity().getCategoryName());

        return productsDTO;
    }

    private ProductsEntity mapToEntity(ProductsDTO productsDTO) {
        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setQuantity(productsDTO.getQuantity());
        productsEntity.setProductDetails(productsDTO.getProductDetails());
        productsEntity.setProductPhoto(productsDTO.getProductPhoto());
        productsEntity.setProductName(productsDTO.getProductName());
        productsEntity.setProductPrice(productsDTO.getProductPrice());

        return productsEntity;
    }

    private ProductsPaginationDTO mapToPagination(Page<ProductsEntity> productsEntities) {
        ProductsPaginationDTO productsPaginationDTO = new ProductsPaginationDTO();
        productsPaginationDTO.setPageNo(productsEntities.getNumber());
        productsPaginationDTO.setPageSize(productsEntities.getSize());
        productsPaginationDTO.setTotalElements(productsEntities.getTotalElements());
        productsPaginationDTO.setTotalPages(productsEntities.getTotalPages());
        productsPaginationDTO.setLast(productsEntities.isLast());

        return productsPaginationDTO;
    }
}
