package ru.devegang.fcs_server.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.User;
import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.repositories.UserRepository;

import java.util.*;

@Service
public class UserService implements UserServiceInterface{


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> getUser(String login) {

        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> getUser(long id) {

        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUser(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);


        if(optionalUser.isPresent()
                && passwordEncoder.matches(password,optionalUser.get().getPassword())) {
            return optionalUser;
        }

        return Optional.empty();
    }

    boolean checkName(String name){
        return (name != null && !name.isBlank());
    }

    boolean isExist(String login) {
        return userRepository.existsUserByLogin(login);
    }

    boolean checkUser(User user) {
        return checkName(user.getName()) && !isExist(user.getLogin());
    }

    @Override
    public Optional<User> createUser(User user) {
        if(checkUser(user)) {
            user.setCharacter_count(0);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return  Optional.of(userRepository.saveAndFlush(user));
        }
        return Optional.empty();
    }

    boolean isExist(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean deleteUser(long id) {
        userRepository.deleteById(id);
        return !isExist(id);
    }

    @Override
    public List<Character> getUsersCharacters(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get().getCharacters();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        Optional<User> opUser = userRepository.findById(user.getId());
        if(opUser.isPresent()) {
            User oldUser = opUser.get();
            if((user.getLogin().equals(oldUser.getLogin())&&checkName(user.getName()))||(checkUser(user))) {
                userRepository.saveAndFlush(user);
                return true;
            }
        }
        return false;
    }

}

