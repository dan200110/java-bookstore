package bookstore.bookstore.service;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.model.CartItemsEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemsServiceInterface {
    Optional<List<CartItemsEntity>> getCartItemsEntitiesByUserId(int id);

    Boolean saveCartItem(String jwtToken, CartItemsDTO cartItemsDTO);
}
