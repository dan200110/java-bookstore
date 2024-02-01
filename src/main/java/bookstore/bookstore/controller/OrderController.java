package bookstore.bookstore.controller;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.dto.OrderItemsDTO;
import bookstore.bookstore.dto.OrdersDTO;
import bookstore.bookstore.exception.ProductOutOfStockException;
import bookstore.bookstore.service.impl.OrdersService;
import bookstore.bookstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrdersService ordersService;
    private final JwtUtils jwtUtils;

    @PostMapping("create_by_product_id")
    public ResponseEntity<String> createOrderByProductId(
            @RequestHeader(name = "Authorization") String authHeader,
            @RequestParam int productId,
            @RequestParam int quantity
    ) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        try {
            ordersService.createOrderByProductId(jwtToken, productId, quantity);
            return ResponseEntity.ok("Order created successfully");
        } catch (ProductOutOfStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order creation failed. Please try again.");
        }
    }

    @PostMapping("create_by_cart")
    public ResponseEntity<String> createOrderByCart(
            @RequestHeader(name = "Authorization") String authHeader
    ) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        try {
            ordersService.createOrderByCart(jwtToken);
            return ResponseEntity.ok("Order created successfully");
        } catch (ProductOutOfStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order creation failed. Please try again.");
        }
    }

}
