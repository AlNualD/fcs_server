package ru.devegang.fcs_server.controllers.v2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.User;
import ru.devegang.fcs_server.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2")
public class UserControllerV2 {

    @Autowired
    UserService userService;


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Optional<User> optionalUser = userService.createUser(user);
        return optionalUser.isPresent()? new ResponseEntity<>(optionalUser.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users")
    public ResponseEntity<?> update(@RequestParam long user_id, @RequestBody User user) {
        user.setId(user_id);
        return userService.updateUser(user)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }




    @GetMapping("/users/login/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {
        Optional<User> optionalUser = userService.getUser(login);
        return optionalUser.isPresent()? new ResponseEntity<>(optionalUser.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping("/users/characters/{user_id}")
    public ResponseEntity<List<Character>> getAllCharacterByUser(@PathVariable(name = "user_id") long id) {
        return new ResponseEntity<>(userService.getUsersCharacters(id), HttpStatus.OK);
        }

     @GetMapping("/users/{user_id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "user_id") long user_id) {
        Optional<User> optionalUser = userService.getUser(user_id);
        return optionalUser.isPresent()? new ResponseEntity<>(optionalUser.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

}
