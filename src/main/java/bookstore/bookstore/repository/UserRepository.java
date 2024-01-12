package bookstore.bookstore.repository;

import bookstore.bookstore.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    Boolean existsByUserName(String userName);
}
