package bookstore.bookstore.repository;

import bookstore.bookstore.model.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity, Integer> {
}
