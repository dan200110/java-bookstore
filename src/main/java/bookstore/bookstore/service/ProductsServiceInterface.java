package bookstore.bookstore.service;

import bookstore.bookstore.dto.ProductsPaginationDTO;
import bookstore.bookstore.dto.ProductsDTO;
import bookstore.bookstore.model.ProductsEntity;

import java.util.List;
import java.util.Optional;

public interface ProductsServiceInterface {
    ProductsPaginationDTO getAllProductsPagination(int pageNo, int pageSize, String sortBy, String sortDir);

    Optional<List<ProductsDTO>> findAllProducts();

    Optional<ProductsEntity> findProductById(int id);

    Boolean createProduct(String jwtToken, ProductsDTO productsDTO);

    ProductsPaginationDTO getProductsByName(String query, int pageNo, int pageSize, String sortBy, String sortDir);
}
