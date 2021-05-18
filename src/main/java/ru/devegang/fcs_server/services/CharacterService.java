package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.additional.dnd5.Attributes;
import ru.devegang.fcs_server.additional.dnd5.Classes;
import ru.devegang.fcs_server.additional.dnd5.Races;
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

    public boolean addRaceAndClass(long character_id, String race, String cclass) {
        Optional<Character> optionalCharacter = getCharacter(character_id);
        if(!optionalCharacter.isPresent()) return false;
        Character character = optionalCharacter.get();
        setRace(race,character);
        character.setClassC(cclass);
        setCClass(cclass,character);
        return true;
    }

    private void setRace(String race, Character character) {
        List<Attribute> attributes = character.getAttributes();
        switch (Races.getRace(race)) {
            case Elf:attributes.get(Attributes.Dexterity.getIndex()).setAmount(12);
                attributes.get(Attributes.Intelligence.getIndex()).setAmount(12);
                character.setRace("Эльф");
                break;
            case Dwarf: attributes.get(Attributes.Strength.getIndex()).setAmount(12);
                character.setRace("Дворф");
                break;
            case Halfling: attributes.get(Attributes.Charisma.getIndex()).setAmount(11);
                character.setRace("Полурослик");
                break;
            case Human:attributes.forEach(atr -> atr.incrementAmount());
                character.setRace("Человек");
                break;
            case Dragonborn: attributes.get(Attributes.Charisma.getIndex()).setAmount(11);
                attributes.get(Attributes.Strength.getIndex()).setAmount(12);
                character.setRace("Драконорожденный");
                break;
            case Gnome: attributes.get(Attributes.Dexterity.getIndex()).setAmount(11);
                character.setRace("Гном");
                break;
            default: character.setRace("");

        }
    }

    private void setCClass(String cclass, Character character) {
        switch (Classes.getClass(cclass)) {
            case Barbarian:
                character.set1lvlHp(12);

                break;
            case Bard:
                character.set1lvlHp(8);
                character.setSpells_total(4);
                character.getSlots().setLvl0(2);
                character.getSlots().setLvl1(2);
                break;
            case Cleric:
                character.set1lvlHp(8);
                character.setSpells_total(-1);
                character.getSlots().setLvl0(3);
                character.getSlots().setLvl1(2);
                break;
            case Druid:
                character.set1lvlHp(8);
                character.setSpells_total(-1);
                character.getSlots().setLvl0(2);
                character.getSlots().setLvl1(2);
                break;
            case Fighter:
                character.set1lvlHp(10);
                break;
            case Monk:
                character.set1lvlHp(8);
                break;
            case Paladin:
                character.set1lvlHp(10);
                character.setSpells_total(-1);
                break;
            case Ranger:
                character.set1lvlHp(10);
                character.setSpells_total(0);
                break;
            case Rouge:
                character.set1lvlHp(8);
                break;
            case Sorcerer:
                character.set1lvlHp(6);
                character.setSpells_total(2);
                character.getSlots().setLvl0(4);
                character.getSlots().setLvl1(2);
                break;
            case Warlock:
                character.set1lvlHp(8);
                character.setSpells_total(2);
                character.getSlots().setLvl0(2);
                character.getSlots().setLvl1(1);
                break;
            case Wizard:
                character.set1lvlHp(6);
                character.setSpells_total(-1);
                character.getSlots().setLvl0(3);
                character.getSlots().setLvl1(2);
                break;
        }
    }
}
