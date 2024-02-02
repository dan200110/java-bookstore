package bookstore.bookstore.repository;

import bookstore.bookstore.dto.ProductReviewsDTO;
import bookstore.bookstore.model.ProductReviewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewsRepository extends JpaRepository<ProductReviewsEntity, Integer> {
    Page<ProductReviewsEntity> findAllByProductsEntity_Id(int productId, Pageable pageable);
}
