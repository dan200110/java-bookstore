package bookstore.bookstore.service;

import bookstore.bookstore.model.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoriesServiceInterface {
    Optional<List<CategoryEntity>> getAllCategory();
}
