package ru.devegang.fcs_server.services;
import ru.devegang.fcs_server.entities.*;
public interface UserServiceInterface {
     User getUser(String login);
     void createUser(User user);
}
