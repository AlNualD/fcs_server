package ru.devegang.fcs_server.services;
import ru.devegang.fcs_server.entities.*;
public interface UserServiceInterface {
     userFORTEST getUser(String login);
     void createUser(userFORTEST user);
}
