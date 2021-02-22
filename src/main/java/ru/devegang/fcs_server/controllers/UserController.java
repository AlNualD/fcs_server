package ru.devegang.fcs_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.User;
import ru.devegang.fcs_server.repositories.CharacterRepository;
import ru.devegang.fcs_server.repositories.UserRepository;
//import ru.devegang.fcs_server.services.UserService;
import ru.devegang.fcs_server.entities.userFORTEST;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
//    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    public UserController (UserService userService) {
//        this.userService = userService;
//    }
//
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if(userRepository.findByLogin(user.getLogin()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> users = userRepository.findAll();
        return users != null && !users.isEmpty() ? new ResponseEntity<>(users,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/users/login/{logn}")
    public ResponseEntity<User> getUserbyLogin(@PathVariable(name = "login") String login) {
        final Optional<User> Opuser = userRepository.findByLogin(login);
        return Opuser.isPresent() ? new ResponseEntity<>(Opuser.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @GetMapping("/users/characters/{user_id}")
    public ResponseEntity<List<Character>> getAllCharacterByUser(@PathVariable(name = "user_id") long id) {
        final Optional<User> Opuser = userRepository.findById(id);
        System.out.println("Get by USERID " + id);
        if (!Opuser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final User user = Opuser.get();
        System.out.println("mmmm");
        final List<Character> characters = user.getCharacters();//characterRepository.findAllByUser(userRepository.findById(id));
        return characters != null && !characters.isEmpty() ? new ResponseEntity<>(characters,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//    public List<User> getAllUsers(){
//        return userRepository.findAll();
//    }

//    @PostMapping(value = "/users")
//    public ResponseEntity<?> create(@RequestBody User user ) {
//        userService.createUser(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

//    @GetMapping(value = "/users/{login}")
//    public ResponseEntity<User> get(@PathVariable(name = "name") String login) {
//      final User user = userService.getUser(login);
//      return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


}
