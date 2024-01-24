package bookstore.bookstore.controller;

import bookstore.bookstore.DTO.UserDTO;
import bookstore.bookstore.model.JwtModel;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.UserService;
import bookstore.bookstore.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("")
    public List<UsersEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping ("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody UserDTO userDTO) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setPassword(userDTO.getPassword());
        usersEntity.setUserName(jwtUtils.getUserNameFromJwtToken(userDTO.getJwt()));

        if (userService.updateUser(usersEntity).isEmpty()) {
            return new ResponseEntity<>("User failed to update", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @PostMapping ("/get_user")
    public ResponseEntity<UsersEntity> getUser(@RequestBody JwtModel jwtModel) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtModel.getJwt());
        Optional<UsersEntity> usersEntity = userService.getUserByUserName(username);

        if (usersEntity.isEmpty()) {
            return new ResponseEntity<>(usersEntity.get(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(usersEntity.get(), HttpStatus.OK);
    }
}

