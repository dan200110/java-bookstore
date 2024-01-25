package bookstore.bookstore.service;

import bookstore.bookstore.model.CartItemsEntity;
import bookstore.bookstore.repository.CartItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemsService implements CartItemsServiceInterface {
    private final CartItemsRepository cartItemsRepository;
    @Override
    public Optional<List<CartItemsEntity>> getCartItemsEntitiesByUserId(int id) {
        return cartItemsRepository.findCartItemsEntitiesByUserId(id);
    }

    @Override
    public CartItemsEntity addCartItem(CartItemsEntity cartItem) {
        // Add any additional logic or validation before saving the cart item
        return cartItemsRepository.save(cartItem);
    }

    @Override
    public Boolean saveCartItem(CartItemsEntity cartItemsEntity) {
        try {
            cartItemsRepository.save(cartItemsEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
