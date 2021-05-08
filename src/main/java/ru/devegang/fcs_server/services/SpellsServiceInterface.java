package ru.devegang.fcs_server.services;

import ru.devegang.fcs_server.entities.Spell;

import java.util.List;
import java.util.Optional;

public interface SpellsServiceInterface {
    Optional<Spell>  createSpell(long character_id, Spell spell);
    Optional<Spell> getSpell(long id);
    boolean deleteSpell(long id);
    boolean updateSpell(Spell spell);
    List<Spell> getSpells();
    List<Spell> getCharacterSpells(long character_id);
    List<Spell> getUserSpells(long user_id);
}
