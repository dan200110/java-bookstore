package bookstore.bookstore.controller;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.dto.JwtModel;
import bookstore.bookstore.model.CartItemsEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.CartItemsService;
import bookstore.bookstore.service.UserService;
import bookstore.bookstore.util.JwtUtils;
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
    public ResponseEntity<List<CartItemsEntity>> getCartItems(@RequestHeader(name = "Authorization") String authHeader) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);
        if (usersEntity.isPresent()) {
            List<CartItemsEntity> cartItemsEntityList = cartItemsService.getCartItemsEntitiesByUserId(usersEntity.get().getId())
                    .orElse(Collections.emptyList());

            return new ResponseEntity<>(cartItemsEntityList, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/add_cart_item")
    public ResponseEntity<String> addCartItem(@RequestHeader(name = "Authorization") String authHeader, @RequestBody CartItemsDTO cartItemsDTO) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        if (Boolean.TRUE.equals(cartItemsService.saveCartItem(jwtToken, cartItemsDTO))) {
            return new ResponseEntity<>("Adding new cart items is successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to add new cart items", HttpStatus.BAD_REQUEST);
    }
}
