package bookstore.bookstore.service.impl;

import bookstore.bookstore.dto.UserDTO;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.UserRepository;
import bookstore.bookstore.service.UserServiceInterface;
import bookstore.bookstore.service.impl.MyUserDetailsService;
import bookstore.bookstore.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MyUserDetailsService myUserDetailsService;
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

    public boolean changePassword(String jwtToken, UserDTO userDTO) {
        return getUserByJwtToken(jwtToken)
                .filter(user -> passwordEncoder.matches(userDTO.getCurrentPassword(), user.getPassword()))
                .map(user -> {
                    try {
                        user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
                        userRepository.save(user);
                        return true;
                    } catch (Exception e) {
                        log.error("Error while updating password", e);
                    }
                    return false;
                })
                .orElse(false);
    }

    @Override
    public Optional<UsersEntity> getUserByUserName(String userName) {
        return userRepository.findUsersEntityByUserName(userName);
    }

    @Override
    @Transactional
    public Optional<UsersEntity> getUserByJwtToken(String jwt) {
        return userRepository.findUsersEntityByUserName(jwtUtils.getUserNameFromJwtToken(jwt));
    }
    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
