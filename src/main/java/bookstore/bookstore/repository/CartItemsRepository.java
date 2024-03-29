package bookstore.bookstore.repository;

import bookstore.bookstore.model.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItemsEntity, Integer> {
    Optional<List<CartItemsEntity>> findCartItemsEntitiesByUserId(int id);

    Optional<CartItemsEntity> findCartItemsEntitiesById(int id);

    void deleteByUserId(int userId);

    boolean existsByIdAndUserId(Integer id, int id1);

    void deleteByIdIn(List<Integer> validCartItemIds);
}
