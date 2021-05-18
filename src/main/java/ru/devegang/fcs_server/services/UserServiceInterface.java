package ru.devegang.fcs_server.services;
import ru.devegang.fcs_server.entities.*;
import ru.devegang.fcs_server.entities.Character;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
     Optional<User> getUser(String login);
     Optional<User> getUser(long id);
     Optional<User> createUser(User user);
     boolean deleteUser(long id);
     List<Character> getUsersCharacters(long id);
     boolean updateUser(User user);
//     boolean addCharacterToUserList(long user_id, long character_id);
//     boolean deleteCharacterFromUserList(long user_id, long character_id);

}
