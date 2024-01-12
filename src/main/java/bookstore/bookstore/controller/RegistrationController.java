package bookstore.bookstore.controller;

import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UsersEntity usersEntity) {
        return userService.registerUser(usersEntity);
    }
}
