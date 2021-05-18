package ru.devegang.fcs_server.controllers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.entities.*;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.repositories.*;
import ru.devegang.fcs_server.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2")
public class CharacterController {
    @Autowired
    CharacterService characterService;
    @Autowired
    UserService userService;
    @Autowired
    SkillsService skillsService;
    @Autowired
    SpellsService spellsService;
    @Autowired
    ItemsService itemsService;

//    @Autowired
//    CharacterRepository characterRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    SkillsRepository skillsRepository;
//    @Autowired
//    SpellRepository spellRepository;
//    @Autowired
//    ItemRepository itemRepository;



    @GetMapping("/characters/{id}")
    public ResponseEntity<Character> getCharacterByID(@PathVariable("id") long character_id) {
        final Optional<Character> optionalCharacter = characterService.getCharacter(character_id);
        return optionalCharacter.isPresent() ? new ResponseEntity<>(optionalCharacter.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/characters")
    public ResponseEntity<Character> createCharacter(@RequestParam(name = "user_id") long user_id, @RequestBody Character character) {
        Optional<Character> characterOptional = characterService.createCharacter(user_id,character);
        return characterOptional.isPresent() ? new ResponseEntity<>(characterOptional.get(),HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/characters")
    public ResponseEntity<?> updateCharacter(@RequestParam(name = "character_id") long character_id , @RequestBody Character character) {
        character.setId(character_id);
        return characterService.updateCharacter(character) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/characters")
    public ResponseEntity<?> deleteCharacter(@RequestParam(name = "character_id") long id) {
        return characterService.deleteCharacter(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/characters/skills")
    public ResponseEntity<List<Skill>> getCharacterSkills(@RequestParam(name = "character_id") long id) {
        return new ResponseEntity<>(skillsService.getCharacterSkills(id), HttpStatus.OK);
        }

    @GetMapping("/characters/skills/{id}")
    public ResponseEntity<Skill> getSkillByID(@PathVariable("id") long skillID) {
        Optional<Skill> skillOptional = skillsService.getSkill(skillID);
        return skillOptional.isPresent() ? new ResponseEntity<>(skillOptional.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/characters/skills/user")
    public ResponseEntity<List<Skill>> getSkillsByUser(@RequestParam("user_id") long user_id) {
        return new ResponseEntity<>(skillsService.getUserSkills(user_id),HttpStatus.OK);
       }


    @PostMapping("/characters/skills")
    public ResponseEntity<Skill> createSkill(@RequestParam(name = "character_id") long character_id, @RequestBody Skill skill) {
        Optional<Skill> skillOptional = skillsService.createSkill(character_id,skill);
        return skillOptional.isPresent()? new ResponseEntity<>(skillOptional.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/characters/skills")
    public ResponseEntity<Skill> createSkill(@RequestParam(name = "character_id") long character_id,@RequestBody Attribute attribute, @RequestBody Skill skill) {
        Optional<Skill> skillOptional = skillsService.createSkill(character_id,attribute,skill);
        return skillOptional.isPresent()? new ResponseEntity<>(skillOptional.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/characters/skills")
    public ResponseEntity<Skill> createSkill(@RequestParam(name = "character_id") long character_id,@RequestParam(name = "attribute_id") long attribute_id, @RequestBody Skill skill) {
        Optional<Skill> skillOptional = skillsService.createSkill(character_id,attribute_id,skill);
        return skillOptional.isPresent()? new ResponseEntity<>(skillOptional.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/characters/skills")
    public ResponseEntity<?> updateSkill(@RequestParam long skill_id ,@RequestBody Skill skill) {
        skill.setId(skill_id);
        return  skillsService.updateSkill(skill) ? new ResponseEntity<>(HttpStatus.OK) :  new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/characters/skills")
    public ResponseEntity<?> deleteSkill(@RequestParam(name = "skill_id") long skill_id) {
        return skillsService.deleteSkill(skill_id)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/characters/spells")
    public ResponseEntity<List<Spell>> getCharacterSpells(@RequestParam(name = "character_id") long id) {
        return new ResponseEntity<>(spellsService.getCharacterSpells(id),HttpStatus.OK);
    }

    @GetMapping("/characters/spells/{id}")
    public ResponseEntity<Spell> getSpellById(@PathVariable("id") long id) {
        Optional<Spell> spellOptional = spellsService.getSpell(id);
        return spellOptional.isPresent() ? new ResponseEntity<>(spellOptional.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/characters/spells")
    public ResponseEntity<?> createSpell(@RequestParam(name = "character_id") long character_id, @RequestBody Spell spell) {
        Optional<Spell> spellOptional = spellsService.createSpell(character_id,spell);
        return spellOptional.isPresent()? new ResponseEntity<>(spellOptional.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/characters/spells/user")
    public ResponseEntity<List<Spell>> getSpellsByUser(@RequestParam("user_id") long userID) {
        return new ResponseEntity<>(spellsService.getUserSpells(userID),HttpStatus.OK);
    }

    @PutMapping("/characters/spells")
    public ResponseEntity<Spell> updateSpell(@RequestParam(name = "spell_id") long spell_id, @RequestBody Spell spell) {
        spell.setId(spell_id);
        return spellsService.updateSpell(spell) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping("characters/spells")
    public ResponseEntity<?> deleteSpell(@RequestParam(name = "spell_id") long spell_id) {
        return spellsService.deleteSpell(spell_id)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/characters/inventory")
    public ResponseEntity<List<Item>> getCharacterInventory(@RequestParam(name = "character_id") long character_id) {
        return new ResponseEntity<>(itemsService.getItems(character_id), HttpStatus.OK);
    }

    @PostMapping("/characters/inventory")
    public ResponseEntity<?> createItem(@RequestParam(name = "character_id") long character_id, @RequestBody Item item) {
        Optional<Item> itemOptional = itemsService.createItem(character_id,item);
        return itemOptional.isPresent()? new ResponseEntity<>(itemOptional.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/characters/inventory")
    public ResponseEntity<?> updateItem(@RequestParam(name = "item_id")long item_id, @RequestBody Item item) {
        item.setId(item_id);
        return itemsService.updateItem(item)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/characters/inventory")
    public ResponseEntity<?> deleteItem(@RequestParam(name = "item_id") long item_id) {
        return itemsService.deleteItem(item_id)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}