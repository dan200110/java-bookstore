package bookstore.bookstore.service;

import bookstore.bookstore.model.CartItemsEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemsServiceInterface {
    Optional<List<CartItemsEntity>> getCartItemsEntitiesByUserId(int id);
    CartItemsEntity addCartItem(CartItemsEntity cartItem);

    Boolean saveCartItem(CartItemsEntity cartItemsEntity);
}
