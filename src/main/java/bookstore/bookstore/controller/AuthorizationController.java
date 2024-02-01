package bookstore.bookstore.controller;


import bookstore.bookstore.dto.JwtModel;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.impl.MyUserDetailsService;
import bookstore.bookstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final MyUserDetailsService myUserDetailsService;

    @PostMapping
    public ResponseEntity<JwtModel> getJwtToken(@RequestBody UsersEntity usersEntity) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usersEntity.getUserName(), usersEntity.getPassword()));

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(usersEntity.getUserName());
            return new ResponseEntity<>(new JwtModel(jwtUtils.generateJwtToken(userDetails)), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.error("Incorrect username or password", e);
        }

        return new ResponseEntity<>(new JwtModel(), HttpStatus.BAD_REQUEST);
    }
}
