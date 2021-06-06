package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.additional.RollingFormula;
import ru.devegang.fcs_server.entities.Attribute;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.Spell;
import ru.devegang.fcs_server.repositories.SpellRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
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
        Optional<Spell> optionalSpell = getSpell(spell.getId());
        if(optionalSpell.isPresent()) {
            Spell old = optionalSpell.get();
            if(!spell.getFormula().isEmpty() && !RollingFormula.checkStringFormula(spell.getFormula())) return false;
            old.setFormula(spell.getFormula());
            old.setName(spell.getName());
            old.setDescription(spell.getDescription());
            old.setDefinition(spell.getDefinition());
            old.setLvl(spell.getLvl());
            if(spell.getDifficulty() == -1 && old.getAttribute() != null) {
                old.updateDifficulty();
            } else {
                old.setDifficulty(spell.getDifficulty());
            }
            spellRepository.saveAndFlush(old);
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

    public  boolean setFavorite(long spell_id, boolean isFavorite) {
        Optional<Spell> optionalSpell = getSpell(spell_id);
        if(optionalSpell.isPresent()) {
            Spell spell = optionalSpell.get();
            spell.setFavorite( isFavorite);
            spellRepository.saveAndFlush(spell);
            return true;
        }
        return false;
    }

    public boolean setAttribute(long spell_id, Attribute attribute) {
        Optional<Spell> optionalSpell = getSpell(spell_id);
        if(optionalSpell.isPresent()) {
            Spell spell = optionalSpell.get();
            spell.setAttribute(attribute);
            spellRepository.saveAndFlush(spell);
            return true;
        }
        return false;
    }
}
