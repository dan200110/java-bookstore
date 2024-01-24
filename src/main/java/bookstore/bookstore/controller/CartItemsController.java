package bookstore.bookstore.controller;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.model.CartItemsEntity;
import bookstore.bookstore.model.JwtModel;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.CartItemsService;
import bookstore.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemsController {
    private final CartItemsService cartItemsService;
    private final UserService userService;

    @GetMapping("/get_cart_items")
    public ResponseEntity<List<CartItemsEntity>> getCartItems(@RequestBody JwtModel jwtModel) {
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtModel.getJwt());
        if (usersEntity.isPresent()) {
            List<CartItemsEntity> cartItemsEntityList = cartItemsService.getCartItemsEntitiesByUserId(usersEntity.get().getId())
                    .orElse(Collections.emptyList());

            return new ResponseEntity<>(cartItemsEntityList, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/add_cart_item")
    public ResponseEntity<String> addCartItem(@RequestBody CartItemsDTO cartItemsDTO) {
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(cartItemsDTO.getJwt());

        if (usersEntity.isPresent()) {

            CartItemsEntity cartItemsEntity = new CartItemsEntity();
            cartItemsEntity.setUserId(usersEntity.get().getId());
            cartItemsEntity.setProductId(cartItemsDTO.getProductId());
            cartItemsEntity.setQuantity(cartItemsDTO.getQuantity());

            if (Boolean.TRUE.equals(cartItemsService.saveCartItem(cartItemsEntity))) {
                return new ResponseEntity<>("Adding new cart items is successful", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Failed to add new cart items", HttpStatus.BAD_REQUEST);
    }
}
