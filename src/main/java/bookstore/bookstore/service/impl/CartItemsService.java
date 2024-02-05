package bookstore.bookstore.service.impl;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.model.CartItemsEntity;
import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.CartItemsRepository;
import bookstore.bookstore.service.CartItemsServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemsService implements CartItemsServiceInterface {
    private final CartItemsRepository cartItemsRepository;
    private final UserService userService;
    private final ProductsService productsService;

    private Optional<List<CartItemsEntity>> getCartItemsEntitiesByUserId(int id) {
        return cartItemsRepository.findCartItemsEntitiesByUserId(id);
    }

    private CartItemsDTO mapToDTO(CartItemsEntity cartItemsEntity) {
        CartItemsDTO dto = new CartItemsDTO();
        dto.setProductId(cartItemsEntity.getProductsEntity().getId());
        dto.setQuantity(cartItemsEntity.getQuantity());
        dto.setId(cartItemsEntity.getId());
        dto.setUserId(cartItemsEntity.getUserId());
        // Map other attributes as needed
        return dto;
    }

    // Map a list of CartItemsEntity to a list of CartItemsResponseDTO
    private List<CartItemsDTO> mapToDTOList(List<CartItemsEntity> cartItemsEntities) {
        return cartItemsEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<List<CartItemsDTO>> getCartItemsByUserId(int userId) {
        Optional<List<CartItemsEntity>> cartItemsEntities = getCartItemsEntitiesByUserId(userId);
        return cartItemsEntities.map(this::mapToDTOList);
    }
    @Override
    public Boolean saveCartItem(String jwtToken, CartItemsDTO cartItemsDTO) {
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);

        if (usersEntity.isPresent()) {
            Optional<ProductsEntity> productsEntity = productsService.findProductById(cartItemsDTO.getProductId());

            if (productsEntity.isEmpty() || productsEntity.get().getQuantity() < cartItemsDTO.getQuantity()) {
                return false;
            }

            CartItemsEntity cartItemsEntity = new CartItemsEntity();
            cartItemsEntity.setUserId(usersEntity.get().getId());
            cartItemsEntity.setProductsEntity(productsEntity.get());
            cartItemsEntity.setQuantity(cartItemsDTO.getQuantity());

            cartItemsRepository.save(cartItemsEntity);
            return true;
        }

        return false;
    }

    @Override
    public Boolean deleteCartItemById(String jwtToken, int cartItemId) {
        if (isCorrectCartItemEntity(jwtToken, cartItemId)) {
            cartItemsRepository.deleteById(cartItemId);
            return true;
        }

        return false;
    }

    private boolean isCorrectCartItemEntity(String jwtToken, int cartItemId) {
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);
        Optional<CartItemsEntity> cartItemsEntity = cartItemsRepository.findCartItemsEntitiesById(cartItemId);
        return usersEntity.isPresent() && cartItemsEntity.isPresent() && cartItemsEntity.get().getUserId() == usersEntity.get().getId();
    }

    @Override
    public Boolean updateCartItemQuantityById(String jwtToken, int cartItemId, int quantity) {
        Optional<CartItemsEntity> cartItemsEntity = cartItemsRepository.findCartItemsEntitiesById(cartItemId);
        if (isCorrectCartItemEntity(jwtToken, cartItemId) && quantity <= cartItemsEntity.get().getProductsEntity().getQuantity()) {
            try {
                cartItemsEntity.get().setQuantity(quantity);
                cartItemsRepository.save(cartItemsEntity.get());
                return true;
            } catch (Exception e) {
                e.printStackTrace();  // Consider logging the exception instead of printing stack trace
            }
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean deleteAllCartItems(String jwtToken) {
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);
        int userId = usersEntity.get().getId();
        try {
            cartItemsRepository.deleteByUserId(userId);
            return true;
        } catch (Exception e) {
            log.error("Error while deleting cart items for user with ID: {}", userId, e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean deleteCartItemsByIds(String jwtToken, List<Integer> cartItemIds) {
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);

        if (usersEntity.isPresent()) {
            // Filter IDs that belong to the user
            List<Integer> validCartItemIds = cartItemIds.stream()
                    .filter(id -> cartItemsRepository.existsByIdAndUserId(id, usersEntity.get().getId()))
                    .collect(Collectors.toList());

            // Delete all entities with valid IDs
            cartItemsRepository.deleteByIdIn(validCartItemIds);

            return true;
        }

        return false;
    }

}
