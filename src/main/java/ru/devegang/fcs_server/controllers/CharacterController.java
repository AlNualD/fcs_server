package ru.devegang.fcs_server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.entities.*;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.repositories.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CharacterController {
    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SkillsRepository skillsRepository;
    @Autowired
    SpellRepository spellRepository;
    @Autowired
    ItemRepository itemRepository;


    @GetMapping("/characters")
    public ResponseEntity<List<Character>> getAllCharacter() {
        System.out.println("GWT");
        final List<Character> characters = characterRepository.findAll();
        return !characters.isEmpty() ? new ResponseEntity<>(characters, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/characters")
    public ResponseEntity<?> createCharacter(@RequestParam(name = "id") long id, @RequestBody Character character) {
        final Optional<User> Opuser = userRepository.findById(id);
        System.out.println("Get by USERID " + id);
        if (!Opuser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final User user = Opuser.get();
        character.setUser(user);
        characterRepository.save(character);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/characters/skills")
    public ResponseEntity<List<Skill>> getCharacterSkills(@RequestParam(name = "id") long id) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if(!characterOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Character character = characterOptional.get();
        System.out.println("char " + character.toString());
        final List<Skill> skills = character.getSkills();
        return skills!=null && !skills.isEmpty() ? new ResponseEntity<>(skills,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/characters/skills")
    public ResponseEntity<?> createSkill(@RequestParam(name = "id") long id, @RequestBody Skill skill) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if(!characterOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Character character = characterOptional.get();
        skill.setCharacter(character);
        skillsRepository.save(skill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/characters/spells")
    public ResponseEntity<List<Spell>> getCharacterSpells(@RequestParam(name = "id") long id) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if(!characterOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Character character = characterOptional.get();
        System.out.println("char " + character.toString());
        final List<Spell> spells = character.getSpells();
        return spells!=null && !spells.isEmpty() ? new ResponseEntity<>(spells,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/characters/spells")
    public ResponseEntity<?> createSpell(@RequestParam(name = "id") long id, @RequestBody Spell spell) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if(!characterOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Character character = characterOptional.get();
        spell.setCharacter(character);
        spellRepository.save(spell);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/characters/inventory")
    public ResponseEntity<List<Item>> getCharacterInventory(@RequestParam(name = "id") long id) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if(!characterOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Character character = characterOptional.get();
        System.out.println("char " + character.toString());
        final List<Item> inventory = character.getInventory();
        return inventory!=null && !inventory.isEmpty() ? new ResponseEntity<>(inventory,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/characters/inventory")
    public ResponseEntity<?> createItem(@RequestParam(name = "id") long id, @RequestBody Item item) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if(!characterOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Character character = characterOptional.get();
        item.setCharacter(character);
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
