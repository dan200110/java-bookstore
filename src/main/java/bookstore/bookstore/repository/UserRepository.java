package bookstore.bookstore.repository;

import bookstore.bookstore.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    Boolean existsByUserName(String userName);
    Optional<UsersEntity> findUsersEntityByUserName(String username);
}
