package bookstore.bookstore.service;

import bookstore.bookstore.model.UsersEntity;

import java.util.Optional;

public interface UserServiceInterface {
    Optional<UsersEntity> registerUser(UsersEntity usersEntity);
    Optional<UsersEntity> updateUser(UsersEntity usersEntity);
    Optional<UsersEntity> getUserByUserName(String userName);
    Optional<UsersEntity> getUserByJwtToken(String userName);
}
