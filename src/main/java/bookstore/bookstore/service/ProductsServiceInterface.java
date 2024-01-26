package bookstore.bookstore.service;

//import bookstore.bookstore.dto.NewProductsDTO;

import bookstore.bookstore.dto.NewProductsDTO;
import bookstore.bookstore.dto.ResponseProductsDTO;
import bookstore.bookstore.model.ProductsEntity;

import java.util.List;
import java.util.Optional;

public interface ProductsServiceInterface {
    Optional<List<ResponseProductsDTO>> findAllProducts();

    Optional<ProductsEntity> findProductById(int id);

    Boolean createProduct(String jwtToken, NewProductsDTO newProductsDTO);
}
