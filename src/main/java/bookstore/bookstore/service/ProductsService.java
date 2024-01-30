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
        Optional<CategoryEntity> existCategoryEntity = categoryService.findCategoryEntityByCategoryName(productsDTO.getCategoryName());
        if (usersEntity.isPresent()) {
            CategoryEntity categoryEntity = new CategoryEntity();

            if (existCategoryEntity.isEmpty()) {
                categoryEntity.setCategoryName(productsDTO.getCategoryName());
            } else {
                categoryEntity = existCategoryEntity.get();
            }

            ProductsEntity productsEntity = mapToEntity(productsDTO);
            productsEntity.setUsersEntity(usersEntity.get());

            try {
                productsRepository.save(productsEntity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
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
