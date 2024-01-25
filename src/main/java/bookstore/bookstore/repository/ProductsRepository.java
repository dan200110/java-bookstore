package bookstore.bookstore.repository;

import bookstore.bookstore.model.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {
}
