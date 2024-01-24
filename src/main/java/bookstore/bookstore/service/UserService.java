package bookstore.bookstore.service;

import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public Optional<UsersEntity> registerUser(UsersEntity usersEntity) {
        usersEntity.setUserType("user");
        Optional<UsersEntity> usersEntityOptional = Optional.empty();
        if(!userRepository.existsByUserName(usersEntity.getUserName())){
            String encryptedPassword = passwordEncoder.encode(usersEntity.getPassword());
            usersEntity.setPassword(encryptedPassword);
            usersEntityOptional = Optional.of(userRepository.save(usersEntity));
        }
        return usersEntityOptional;
    }

    @Override
    @Transactional
    public Optional<UsersEntity> updateUser(UsersEntity usersEntity) {
        Optional<UsersEntity> usersEntityOptional = userRepository.findUsersEntityByUserName(usersEntity.getUserName());
        Optional<UsersEntity> result = Optional.empty();

        if(usersEntityOptional.isPresent()){
            String encryptedPassword = passwordEncoder.encode(usersEntity.getPassword());
            usersEntityOptional.get().setPassword(encryptedPassword);
            result = Optional.of(userRepository.save(usersEntityOptional.get()));
        }

        return result;
    }

    @Override
    public Optional<UsersEntity> getUserByUserName(String userName) {
        return userRepository.findUsersEntityByUserName(userName);
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
