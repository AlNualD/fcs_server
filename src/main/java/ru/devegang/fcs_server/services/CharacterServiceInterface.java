package ru.devegang.fcs_server.services;

import ru.devegang.fcs_server.entities.*;
import ru.devegang.fcs_server.entities.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterServiceInterface {
    Optional<Character> createCharacter(long user_id, Character character);
    boolean deleteCharacter(long character_id);
    List<Character> getUserCharacters(long user_id);
    Optional<Character> getCharacter(long character_id);
    boolean updateCharacter(Character character);
//    List<Spell> getCharacterSpells(long character_id);
//    List<Spell> getUserSpells(long user_id);
//    Spell getSpellFullInf(long spell_id);
//    Optional<Spell> createSpell(long character_id, Spell spell);
//    boolean updateSpell(Spell spell);
//    List<Skill> getCharacterSkills(long character_id);
//    List<Skill> getUserSkills(long user_id);
//    Skill getSkillFullInf(long skill_id);
//    Optional<Skill> createSkill(long character_id, Skill skill);
//    boolean updateSkill(Skill skill);
//    List<Item> getCharacterItems(long character_id);
//    Item getItemFullInf(long item_id);
//    Optional<Item> createItem(Item item);
//    Spells_slots getCharacterSpellsSlots(long character_id);
//    List<Attribute> getCharacterAttributes(long character_id);




}
