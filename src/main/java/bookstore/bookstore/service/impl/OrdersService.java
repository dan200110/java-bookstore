package bookstore.bookstore.service.impl;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.dto.OrderItemsDTO;
import bookstore.bookstore.exception.ProductOutOfStockException;
import bookstore.bookstore.model.*;
import bookstore.bookstore.repository.OrderRepository;
import bookstore.bookstore.service.OrdersServiceInterface;
import bookstore.bookstore.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService implements OrdersServiceInterface {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductsService productsService;
    private final CartItemsService cartItemsService;
    private final JwtUtils jwtUtils;

    private int calculateTotalAmount(List<OrderItemsEntity> orderItemsEntityList) {
        return orderItemsEntityList.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }


    @Override
    @Transactional
    public boolean createOrderByProductId(String jwtToken, int productId, int quantity) {
        Optional<UsersEntity> usersEntity = userService.getUserByUserName(jwtUtils.getUserNameFromJwtToken(jwtToken));

        Optional<ProductsEntity> productsEntity = productsService.findProductById(productId);

        if (usersEntity.isPresent() && productsEntity.isPresent() && productsEntity.get().getQuantity() >= quantity) {
            OrdersEntity ordersEntity = new OrdersEntity();
            ordersEntity.setUsersEntity(usersEntity.get());
            ordersEntity.setStatus(OrderStatus.SUCCESS);

            OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
            orderItemsEntity.setOrdersEntity(ordersEntity);
            orderItemsEntity.setProductsEntity(productsEntity.get());
            orderItemsEntity.setPrice(productsEntity.get().getProductPrice());
            orderItemsEntity.setQuantity(quantity);

            // Deduct quantity from product stock
            productsEntity.get().setQuantity(productsEntity.get().getQuantity() - quantity);

            // Set order items to the order
            ordersEntity.setOrderItemsEntityList(Collections.singletonList(orderItemsEntity));

            // Calculate and set totalAmount
            ordersEntity.setTotalAmount(calculateTotalAmount(ordersEntity.getOrderItemsEntityList()));

            // Save order and order details
            orderRepository.save(ordersEntity);

            return true;
        } else {
            throw new ProductOutOfStockException("Insufficient stock or invalid user for product ID: " + productId);
        }
    }


    @Override
    @Transactional
    public boolean createOrderByCart(String jwtToken) {
        Optional<UsersEntity> usersEntity = userService.getUserByUserName(jwtUtils.getUserNameFromJwtToken(jwtToken));
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setUsersEntity(usersEntity.get());
        ordersEntity.setStatus(OrderStatus.SUCCESS);

        List<OrderItemsEntity> orderItemsEntityList = new ArrayList<>();

        Optional<List<CartItemsDTO>> cartItemsEntityList = cartItemsService.getCartItemsByUserId(usersEntity.get().getId());
        if (cartItemsEntityList.isPresent()) {
            for (CartItemsDTO cartItemsDTO : cartItemsEntityList.get()) {
                Optional<ProductsEntity> productsEntity = productsService.findProductById(cartItemsDTO.getProductId());
                if (productsEntity.isEmpty() || productsEntity.get().getQuantity() < cartItemsDTO.getQuantity()) {
                    throw new ProductOutOfStockException("Insufficient stock for product ID: " + productsEntity.get().getId());
                }

                // Deduct quantity from product stock
                productsEntity.get().setQuantity(productsEntity.get().getQuantity() - cartItemsDTO.getQuantity());

                // Create OrderItem
                OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
                orderItemsEntity.setOrdersEntity(ordersEntity);
                orderItemsEntity.setProductsEntity(productsEntity.get());
                orderItemsEntity.setPrice(productsEntity.get().getProductPrice());
                orderItemsEntity.setQuantity(cartItemsDTO.getQuantity());
                // Add to the list of order details
                orderItemsEntityList.add(orderItemsEntity);
            }
        }


        // Step 4: Save order and order details

        ordersEntity.setOrderItemsEntityList(orderItemsEntityList);
        ordersEntity.setTotalAmount(calculateTotalAmount(ordersEntity.getOrderItemsEntityList()));
        orderRepository.save(ordersEntity);
        cartItemsService.deleteAllCartItems(jwtToken);
        return true;
    }
}
