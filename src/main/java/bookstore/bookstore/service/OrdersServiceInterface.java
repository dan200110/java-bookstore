package bookstore.bookstore.service;

import bookstore.bookstore.dto.OrderItemsDTO;
import bookstore.bookstore.dto.OrdersDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrdersServiceInterface {
    boolean createOrderByProductId(String jwtToken, int productId, int quantity);

    @Transactional
    boolean createOrderByCart(String jwtToken);
}
