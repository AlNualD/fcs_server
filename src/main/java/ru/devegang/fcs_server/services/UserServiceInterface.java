package ru.devegang.fcs_server.services;
import ru.devegang.fcs_server.entities.*;
public interface UserServiceInterface {
     User getUser(String login);
     boolean createUser(User user);
     boolean deleteUser(long id);
     boolean addCharacterToUserList(long user_id, long character_id);
     boolean deleteCharacterFromUserList(long user_id, long character_id);

}
