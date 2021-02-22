//package ru.devegang.fcs_server.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.devegang.fcs_server.entities.User;
//import ru.devegang.fcs_server.entities.userFORTEST;
//import ru.devegang.fcs_server.repositories.UserRepository;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class UserService implements UserServiceInterface {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Transactional
//    @Override
//    public User getUser(String login) {
//        User user = userRepository.findUserByName(login);
//        return user;
//    }
//
//
//
//    @Override
//    public void createUser(User user) {
//
//    }
////    private static final Map<Integer, User> USERS_MAP = new HashMap<>();
////
////    @Override
////    public User getUser(String login) {
////        return USERS_MAP.get(login.hashCode());
////    }
////
////    @Override
////    public void createUser(User user) {
////        USERS_MAP.put(user.getName().hashCode(),user);
////
////    }
//
//
//}
