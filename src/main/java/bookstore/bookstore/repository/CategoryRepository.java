package bookstore.bookstore.repository;

import bookstore.bookstore.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findCategoryEntityByCategoryName(String name);
}
