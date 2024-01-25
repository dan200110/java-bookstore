package bookstore.bookstore.service;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.model.CartItemsEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.CartItemsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemsService implements CartItemsServiceInterface {
    private final CartItemsRepository cartItemsRepository;
    private final UserService userService;
    @Override
    public Optional<List<CartItemsEntity>> getCartItemsEntitiesByUserId(int id) {
        return cartItemsRepository.findCartItemsEntitiesByUserId(id);
    }

    @Override
    public Boolean saveCartItem(String jwtToken, CartItemsDTO cartItemsDTO) {
        return userService.getUserByJwtToken(jwtToken)
                .map(user -> CartItemsEntity.builder()
                        .userId(user.getId())
                        .productId(cartItemsDTO.getProductId())
                        .quantity(cartItemsDTO.getQuantity())
                        .build())
                .map(cartItemsEntity -> {
                    try {
                        cartItemsRepository.save(cartItemsEntity);
                        return true;
                    } catch (Exception e) {
                        log.error("Error while saving cart item", e);
                    }
                    return false;
                })
                .orElse(false);
    }

}
