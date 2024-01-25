package bookstore.bookstore.service;

import bookstore.bookstore.model.ProductsEntity;

import java.util.List;
import java.util.Optional;

public interface ProductsServiceInterface {
    Optional<List<ProductsEntity>> findAllProducts();
}
