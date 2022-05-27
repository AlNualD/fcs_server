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
        Optional<Character> optionalCharacter = getCharacter(character.getId());
        if(optionalCharacter.isPresent()) {
            Character newCharacter  = optionalCharacter.get();
            newCharacter.setClassC(character.getClassC());
            newCharacter.setRace(character.getRace());
            newCharacter.setHp_cur(character.getHp_cur());
            newCharacter.setHp_max(character.getHp_max());
            newCharacter.setDescription(character.getDescription());
            newCharacter.setMoney(character.getMoney());
            newCharacter.setAlignment(character.getAlignment());
            newCharacter.setLvl(character.getLvl());
            newCharacter.setUrl(character.getUrl());
            characterRepository.saveAndFlush(newCharacter);
            return true;
        }
        return false;
    }

    public boolean changeHP(long character_id, int hpcur) {
        Optional<Character> optionalCharacter = getCharacter(character_id);
        if(optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            character.setHp_cur(hpcur);
            characterRepository.saveAndFlush(character);
            return true;
        }
        return false;
    }

    public boolean addPicture(long character_id, String url) {
        Optional<Character> optionalCharacter = getCharacter(character_id);
        if(optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            character.setUrl(url);
            characterRepository.saveAndFlush(character);
            return true;
        }
        return false;
    }

    public boolean lvlUp(long character_id) {
        Optional<Character> optionalCharacter = getCharacter(character_id);
        if(optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            character.setLvl(character.getLvl() + 1);
            int newHP = character.getHealthDice() > 0 ? character.getHp_cur() + character.getHealthDice() / 2 : character.getHp_cur();
            character.setHp_max(newHP);
            character.getSkills().forEach(x -> x.updateModification());
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
        calibrateSkills(character);
        characterRepository.saveAndFlush(character);
        return true;
    }

    private void calibrateSkills(Character character) {
        for (Skill skill : character.getSkills()) {
            skill.updateModification();
        }
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
            case Halfelf: attributes.get(Attributes.Charisma.getIndex()).setAmount(12);
            attributes.get(Attributes.Dexterity.getIndex()).setAmount(11);
            attributes.get(Attributes.Wisdom.getIndex()).setAmount(11);
                break;
            case Halforc: attributes.get(Attributes.Strength.getIndex()).setAmount(12);
            attributes.get(Attributes.Constitution.getIndex()).setAmount(12);
                break;
            case Tiefling:attributes.get(Attributes.Charisma.getIndex()).setAmount(12);
            attributes.get(Attributes.Intelligence.getIndex()).setAmount(11);
                break;

        }
    }

    private void setCClass(String cclass, Character character) {
        switch (Classes.getClass(cclass)) {
            case Barbarian:
                character.firstLvlHp(12);

                break;
            case Bard:
                character.firstLvlHp(8);
                character.setSpells_total(4);
                character.getSlots().setLvl0(2);
                character.getSlots().setLvl1(2);
                break;
            case Cleric:
                character.firstLvlHp(8);
                character.setSpells_total(-1);
                character.getSlots().setLvl0(3);
                character.getSlots().setLvl1(2);
                break;
            case Druid:
                character.firstLvlHp(8);
                character.setSpells_total(-1);
                character.getSlots().setLvl0(2);
                character.getSlots().setLvl1(2);
                break;
            case Fighter:
                character.firstLvlHp(10);
                break;
            case Monk:
                character.firstLvlHp(8);
                break;
            case Paladin:
                character.firstLvlHp(10);
                character.setSpells_total(-1);
                break;
            case Ranger:
                character.firstLvlHp(10);
                character.setSpells_total(0);
                break;
            case Rouge:
                character.firstLvlHp(8);
                break;
            case Sorcerer:
                character.firstLvlHp(6);
                character.setSpells_total(2);
                character.getSlots().setLvl0(4);
                character.getSlots().setLvl1(2);
                break;
            case Warlock:
                character.firstLvlHp(8);
                character.setSpells_total(2);
                character.getSlots().setLvl0(2);
                character.getSlots().setLvl1(1);
                break;
            case Wizard:
                character.firstLvlHp(6);
                character.setSpells_total(-1);
                character.getSlots().setLvl0(3);
                character.getSlots().setLvl1(2);
                break;
        }
    }

    public boolean setCharacterPicture(long character_id, String url) {
        Optional<Character> characterOptional = getCharacter(character_id);
        if(characterOptional.isPresent()) {
            Character character = characterOptional.get();
            character.setUrl(url);
            characterRepository.saveAndFlush(character);
            return  true;
        }
        return false;
    }

}
