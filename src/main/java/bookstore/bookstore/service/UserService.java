package bookstore.bookstore.service;

import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> registerUser(UsersEntity usersEntity) {
        usersEntity.setUserType("user");

        if(userRepository.existsByUserName(usersEntity.getUserName())){
            return new ResponseEntity<>("User name is already taken", HttpStatus.BAD_REQUEST);
        }
        String encryptedPassword = passwordEncoder.encode(usersEntity.getPassword());
        usersEntity.setPassword(encryptedPassword);

        userRepository.save(usersEntity);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
