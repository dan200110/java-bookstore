package bookstore.bookstore.service.impl;

import bookstore.bookstore.model.CategoryEntity;
import bookstore.bookstore.repository.CategoryRepository;
import bookstore.bookstore.service.CategoriesServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService implements CategoriesServiceInterface {
    private final CategoryRepository categoryRepository;

    public void createCategory(String categoryName) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryName);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<List<CategoryEntity>> getAllCategory() {
        return Optional.of(categoryRepository.findAll());
    }
}
