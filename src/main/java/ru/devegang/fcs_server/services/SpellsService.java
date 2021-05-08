package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.Spell;
import ru.devegang.fcs_server.repositories.SpellRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SpellsService implements SpellsServiceInterface {

    @Autowired
    private SpellRepository spellRepository;
    @Autowired
    private CharacterService characterService;

    private boolean checkSpell(Spell spell) {
        return !spell.getName().isBlank() && !spell.getDefinition().isBlank();
    }

    @Override
    public Optional<Spell> createSpell(long character_id, Spell spell) {
        Optional<Character> character= characterService.getCharacter(character_id);
        if(character.isPresent() && checkSpell(spell)) {
            spell.setCharacter(character.get());
            return Optional.of(spellRepository.saveAndFlush(spell));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Spell> getSpell(long id) {
        return spellRepository.findById(id);
    }

    private boolean isExist(long id) {
        return spellRepository.existsById(id);
    }

    @Override
    public boolean deleteSpell(long id) {
        spellRepository.deleteById(id);
        return !isExist(id);
    }

    @Override
    public boolean updateSpell(Spell spell) {
        if(isExist(spell.getId()) && checkSpell(spell)) {
            spellRepository.saveAndFlush(spell);
            return true;
        }
        return false;
    }

    @Override
    public List<Spell> getSpells() {
        return spellRepository.findAll();
    }

    @Override
    public List<Spell> getCharacterSpells(long character_id) {
        Optional<Character> optionalCharacter = characterService.getCharacter(character_id);
        if(optionalCharacter.isPresent()) {
            return optionalCharacter.get().getSpells();
        }
        return null;
    }

    @Override
    public List<Spell> getUserSpells(long user_id) {
        List<Spell> spells = new LinkedList<>();
        List<Character> characters = characterService.getUserCharacters(user_id);
        for (Character character : characters) {
            spells.addAll(character.getSpells());
        }
        return spells;
    }
}
