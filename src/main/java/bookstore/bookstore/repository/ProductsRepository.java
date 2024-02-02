package bookstore.bookstore.repository;

import bookstore.bookstore.model.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {
    @Query("SELECT p FROM ProductsEntity p WHERE " +
            "p.productName LIKE CONCAT('%', :query, '%')" +
            "OR p.productDetails LIKE CONCAT('%', :query, '%')")
    Page<ProductsEntity> searchProducts(String query, Pageable pageable);

    Page<ProductsEntity> findAllByCategoryEntityId(int id, Pageable pageable);
}
