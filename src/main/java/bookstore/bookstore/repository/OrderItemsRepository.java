package bookstore.bookstore.repository;

import bookstore.bookstore.model.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Integer> {
}
