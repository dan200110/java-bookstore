package bookstore.bookstore.service;

//import bookstore.bookstore.dto.NewProductsDTO;

import bookstore.bookstore.dto.NewProductsDTO;
import bookstore.bookstore.dto.ResponseProductsDTO;
import bookstore.bookstore.model.CategoryEntity;
import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.ProductsRepository;
import bookstore.bookstore.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService implements ProductsServiceInterface{
    private final ProductsRepository productsRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final CategoryService categoryService;

    @Override
    public Optional<List<ResponseProductsDTO>> findAllProducts() {
        List<ProductsEntity> productsEntities = productsRepository.findAll()/* Fetch products from repository */;
        List<ResponseProductsDTO> productsDTOList = convertProductsToDTOs(productsEntities);
        return Optional.ofNullable(productsDTOList);
    }

    private List<ResponseProductsDTO> convertProductsToDTOs(List<ProductsEntity> productsEntities) {
        List<ResponseProductsDTO> productsDTOList = new ArrayList<>();

        for (ProductsEntity productsEntity : productsEntities) {
            ResponseProductsDTO productsDTO = new ResponseProductsDTO();
            productsDTO.setProductId(productsEntity.getId());
            productsDTO.setProductName(productsEntity.getProductName());
            productsDTO.setProductPrice(productsEntity.getProductPrice());
            productsDTO.setQuantity(productsEntity.getQuantity());
            productsDTO.setProductPhoto(productsEntity.getProductPhoto());
            productsDTO.setProductDetails(productsEntity.getProductDetails());
            productsDTO.setProviderId(productsEntity.getUsersEntity().getId());
            // Assuming categoryName is derived or obtained from some other source
//            productsDTO.setCategoryName(productsEntity.getCategoryEntity().getCategoryName());

            productsDTOList.add(productsDTO);
        }

        return productsDTOList;
    }

    @Override
    public Optional<ProductsEntity> findProductById(int id) {
        return productsRepository.findById(id);
    }

    @Override
    public Boolean createProduct(String jwtToken, NewProductsDTO newProductsDTO) {
        Optional<UsersEntity> usersEntity = userService.getUserByUserName(jwtUtils.getUserNameFromJwtToken(jwtToken));
        Optional<CategoryEntity> existCategoryEntity = categoryService.findCategoryEntityByCategoryName(newProductsDTO.getCategoryName());
        if (usersEntity.isPresent()) {
            CategoryEntity categoryEntity = new CategoryEntity();

            if (existCategoryEntity.isEmpty()) {
                categoryEntity.setCategoryName(newProductsDTO.getCategoryName());
            } else {
                categoryEntity = existCategoryEntity.get();
            }

            ProductsEntity productsEntity = new ProductsEntity();
            productsEntity.setQuantity(newProductsDTO.getQuantity());
            productsEntity.setProductDetails(newProductsDTO.getProductDetails());
            productsEntity.setProductPhoto(newProductsDTO.getProductPhoto());
            productsEntity.setProductName(newProductsDTO.getProductName());
            productsEntity.setProductPrice(newProductsDTO.getProductPrice());
//            productsEntity.setCategoryEntity(categoryEntity);
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
}
