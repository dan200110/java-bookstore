package bookstore.bookstore.service;

import bookstore.bookstore.model.CategoryEntity;
import bookstore.bookstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<List<CategoryEntity>> findAllCategories() {
        return Optional.of(categoryRepository.findAll());
    }

    public Optional<CategoryEntity> findCategoryEntityByCategoryName(String categoryName) {
        return categoryRepository.findCategoryEntityByCategoryName(categoryName);
    }
}
