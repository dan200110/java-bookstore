package bookstore.bookstore.controller;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.impl.CartItemsService;
import bookstore.bookstore.service.impl.UserService;
import bookstore.bookstore.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemsController {
    private final CartItemsService cartItemsService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/get_cart_items")
    public ResponseEntity<List<CartItemsDTO>> getCartItems(@RequestHeader(name = "Authorization") String authHeader) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);

        if (usersEntity.isPresent()) {
            Optional<List<CartItemsDTO>> cartItemsResponseDTOs = cartItemsService.getCartItemsByUserId(usersEntity.get().getId());

            if (cartItemsResponseDTOs.isPresent()) {
                return new ResponseEntity<>(cartItemsResponseDTOs.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/add_cart_item")
    public ResponseEntity<String> addCartItem(@RequestHeader(name = "Authorization") String authHeader, @RequestBody @Valid CartItemsDTO cartItemsDTO) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        if (Boolean.TRUE.equals(cartItemsService.saveCartItem(jwtToken, cartItemsDTO))) {
            return new ResponseEntity<>("Adding new cart items is successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to add new cart items", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete_cart_item")
    public ResponseEntity<String> deleteCartItem(@RequestHeader(name = "Authorization") String authHeader, @RequestParam int cartItemId) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        if (Boolean.TRUE.equals(cartItemsService.deleteCartItemById(jwtToken, cartItemId))) {
            return new ResponseEntity<>("Delete cart item successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to delete cart item", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete_cart_items")
    public ResponseEntity<String> deleteCartItems(@RequestHeader(name = "Authorization") String authHeader) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        if (Boolean.TRUE.equals(cartItemsService.deleteAllCartItems(jwtToken))) {
            return new ResponseEntity<>("Delete all cart item successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to delete all cart item", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete_cart_item_by_ids")
    public ResponseEntity<String> deleteCartItem(@RequestHeader(name = "Authorization") String authHeader, @RequestParam List<Integer> cartItemIds) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        if (Boolean.TRUE.equals(cartItemsService.deleteCartItemsByIds(jwtToken, cartItemIds))) {
            return new ResponseEntity<>("Delete cart items successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to delete cart items", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update_cart_item_quantity/{id}")
    public ResponseEntity<String> updateCartItem(
            @PathVariable("id") int cartItemId,
            @RequestParam Integer quantity,
            @RequestHeader(name = "Authorization") String authHeader
    ) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        if (Boolean.TRUE.equals(cartItemsService.updateCartItemQuantityById(jwtToken, cartItemId, quantity))) {
            return new ResponseEntity<>("Update cart item successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to update cart item", HttpStatus.BAD_REQUEST);
    }
}
