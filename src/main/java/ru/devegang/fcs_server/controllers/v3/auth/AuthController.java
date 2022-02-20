package ru.devegang.fcs_server.controllers.v3.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.config.jwt.JwtProvider;
import ru.devegang.fcs_server.entities.User;
import ru.devegang.fcs_server.services.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v3")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/register")
    public HttpStatus registerUser(@RequestBody @Valid User user) {

        Optional<User> optionalUser = userService.createUser(user);

        return optionalUser.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        Optional<User> user = userService.getUser(request.getLogin(), request.getPassword());

        if(user.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String token = jwtProvider.generateToken(user.get().getLogin());
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
