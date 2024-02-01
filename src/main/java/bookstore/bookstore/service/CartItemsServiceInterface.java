package bookstore.bookstore.service;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.model.CartItemsEntity;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemsServiceInterface {
    Optional<List<CartItemsDTO>> getCartItemsByUserId(int userId);

    Boolean saveCartItem(String jwtToken, CartItemsDTO cartItemsDTO);

    Boolean deleteCartItemById(String jwtToken, int id);

    Boolean updateCartItemQuantityById(String jwtToken, int cartItemId, int quantity);

    Boolean deleteAllCartItems(String jwtToken);

    @Transactional
    Boolean deleteCartItemsByIds(String jwtToken, List<Integer> cartItemIds);
}
