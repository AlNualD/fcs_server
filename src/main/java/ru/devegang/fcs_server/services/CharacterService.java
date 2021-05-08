package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.entities.*;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.repositories.CharacterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService implements CharacterServiceInterface {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private UserService userService;

    private  boolean checkStr(String str) {
        return str!=null && !str.isBlank();
    }

    private boolean checkCharacter(Character character) {
        return checkStr(character.getName())&&checkStr(character.getRace())&&checkStr(character.getClassC())&&(character.getHp_max()>0);
    }
    @Override
    public Optional<Character> createCharacter(long user_id, Character character) {
        Optional<User> user = userService.getUser(user_id);
        if(user.isPresent() && checkCharacter(character)) {
            character.setUser(user.get());
            return Optional.of(characterRepository.saveAndFlush(character));
        }
        return Optional.empty();
    }

    public boolean isExist(long id){
        return characterRepository.existsById(id);
    }

    @Override
    public boolean deleteCharacter(long character_id) {
        characterRepository.deleteById(character_id);
        return !isExist(character_id);
    }

    @Override
    public List<Character> getUserCharacters(long user_id) {
        return userService.getUsersCharacters(user_id);
    }

    @Override
    public Optional<Character> getCharacter(long character_id) {
        return characterRepository.findById(character_id);
    }

    @Override
    public boolean updateCharacter(Character character) {
        if(isExist(character.getId()) && checkCharacter(character)) {
            characterRepository.saveAndFlush(character);
            return true;
        }
        return false;
    }
}
