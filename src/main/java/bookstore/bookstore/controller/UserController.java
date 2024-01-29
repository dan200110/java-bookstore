package bookstore.bookstore.controller;

import bookstore.bookstore.dto.JwtModel;
import bookstore.bookstore.dto.UserDTO;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.UserService;
import bookstore.bookstore.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("")
    public List<UsersEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping ("/change_password")
    public ResponseEntity<String> changePassword(@RequestHeader(name = "Authorization") String authHeader, @RequestBody UserDTO userDTO) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        if (Boolean.TRUE.equals(userService.changePassword(jwtToken, userDTO))) {
            return new ResponseEntity<>("User changed password successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User failed to change password", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/get_user")
    public ResponseEntity<UsersEntity> getUser(@RequestHeader(name = "Authorization") String authHeader) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        Optional<UsersEntity> usersEntity = userService.getUserByJwtToken(jwtToken);

        if (usersEntity.isPresent()) {
            return new ResponseEntity<>(usersEntity.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new UsersEntity(), HttpStatus.BAD_REQUEST);

    }
}

