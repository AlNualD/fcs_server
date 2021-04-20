package ru.devegang.fcs_server.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.entities.*;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.repositories.*;

import java.util.ArrayList;
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
    @GetMapping("/characters/{id}")
    public ResponseEntity<Character> getCharacterByID(@PathVariable("id") long characterID) {
        final Optional<Character> optionalCharacter = characterRepository.findById(characterID);
        return optionalCharacter.isPresent() ? new ResponseEntity<>(optionalCharacter.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/characters")
    public ResponseEntity<Long> createCharacter(@RequestParam(name = "id") long id, @RequestBody Character character) {
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

    @PutMapping("/characters")
    public ResponseEntity<?> updateCharacter(@RequestBody Character character) {
        Character old = characterRepository.getOne(character.getId());
        old.setName(character.getName());
        old.setAlignment(character.getAlignment());
        old.setClassC(character.getClassC());
        old.setRace(character.getRace());
        old.setLvl(character.getLvl());
        old.setMoney(character.getMoney());
        old.setHp_max(character.getHp_max());
        old.setHp_cur(character.getHp_cur());
        characterRepository.save(old);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/characters/health")
    public ResponseEntity<?> changeHealth(@RequestParam(name = "id") long id ,@RequestParam(name = "newCur") int cur, @RequestParam(name = "newMax") int max) {
        Character character = characterRepository.getOne(id);
        character.setHp_cur(cur);
        character.setHp_max(max);
        characterRepository.save(character);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/characters")
    public ResponseEntity<?> deleteCharacter(@RequestParam(name = "characterID") long id) {
        characterRepository.deleteById(id);
        return !characterRepository.existsById(id) ? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
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
    
    @GetMapping("/characters/skills/{id}")
    public ResponseEntity<Skill> getSkillByID(@PathVariable("id") long skillID) {
        Optional<Skill> skillOptional = skillsRepository.findById(skillID);
        return skillOptional.isPresent() ? new ResponseEntity<>(skillOptional.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/characters/skills/user")
    public ResponseEntity<List<Skill>> getSkillsByUser(@RequestParam("userID") long userID) {
        final Optional<User> optionalUser = userRepository.findById(userID);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final User user = optionalUser.get();
        
        final List<Character> characters = user.getCharacters();
        List<Skill> skills = new ArrayList<>();
        for (Character character : characters) {
            skills.addAll(character.getSkills());
        }
        return !skills.isEmpty() ? new ResponseEntity<>(skills,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @PutMapping("/characters/skills")
    public ResponseEntity<?> updateSkill(@RequestParam(name = "id") long id, @RequestBody Skill skill) {
        Optional<Skill> skillOptional = skillsRepository.findById(id);
        if(!skillOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Skill skillOld = skillOptional.get();
        skillOld.setName(skill.getName());
        skillOld.setDefinition(skill.getDefinition());
        skillOld.setDescription(skill.getDescription());

        skillsRepository.save(skillOld);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/characters/skills")
    public ResponseEntity<?> deleteSkill(@RequestParam(name = "skillId") long id) {
        skillsRepository.deleteById(id);
        return !skillsRepository.existsById(id) ? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
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

    @GetMapping("/characters/spells/user")
    public ResponseEntity<List<Spell>> getSpellsByUser(@RequestParam("userID") long userID) {
        final Optional<User> optionalUser = userRepository.findById(userID);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final User user = optionalUser.get();

        final List<Character> characters = user.getCharacters();
        List<Spell> spells = new ArrayList<>();
        for (Character character : characters) {
            spells.addAll(character.getSpells());
        }
        return !spells.isEmpty() ? new ResponseEntity<>(spells,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/characters/spells")
    public ResponseEntity<?> updateSpell(@RequestParam(name = "id") long id, @RequestBody Spell spell) {
        Optional<Spell> spellOptional = spellRepository.findById(id);
        if(!spellOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Spell spellOld = spellOptional.get();
        spellOld.setName(spell.getName());
        spellOld.setDefinition(spell.getDefinition());
        spellOld.setDescription(spell.getDescription());

        spellRepository.save(spellOld);

        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping("characters/spells")
    public ResponseEntity<?> deleteSpell(@RequestParam(name = "spellID") long id) {
        spellRepository.deleteById(id);
        return !spellRepository.existsById(id) ? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

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
    @DeleteMapping("/characters/inventory")
    public ResponseEntity<?> deleteItem(@RequestParam(name = "itemID") long id) {
        itemRepository.deleteById(id);
        return !itemRepository.existsById(id) ? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
