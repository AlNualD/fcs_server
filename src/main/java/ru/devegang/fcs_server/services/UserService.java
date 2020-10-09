package ru.devegang.fcs_server.services;

import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.entities.userFORTEST;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserServiceInterface {
    private static final Map<Integer, userFORTEST> USERS_MAP = new HashMap<>();

    @Override
    public userFORTEST getUser(String login) {
        return USERS_MAP.get(login.hashCode());
    }

    @Override
    public void createUser(userFORTEST user) {
        USERS_MAP.put(user.getLogin().hashCode(),user);

    }
}
