package ru.devegang.fcs_server.controllers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.additional.dnd5.Classes;
import ru.devegang.fcs_server.additional.dnd5.Races;
import ru.devegang.fcs_server.entities.Attribute;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.Skill;
import ru.devegang.fcs_server.entities.Spells_slots;
import ru.devegang.fcs_server.services.AttributesService;
import ru.devegang.fcs_server.services.CharacterService;
import ru.devegang.fcs_server.services.SkillsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2")
public class DND5Controller {

    @Autowired
    CharacterService characterService;
    @Autowired
    AttributesService attributesService;
    @Autowired
    SkillsService skillsService;


    @GetMapping("/dnd5/races/en")
    public ResponseEntity<List<String>> getRacesEn() {
        return new ResponseEntity<>(Races.getRacesEng(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/races/ru")
    public ResponseEntity<List<String>> getRacesRu() {
        return new ResponseEntity<>(Races.getRacesRus(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/classes/en")
    public ResponseEntity<List<String>> getClassesEn() {
        return new ResponseEntity<>(Classes.getClassesEng(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/classes/ru")
    public ResponseEntity<List<String>> getClassesRu() {
        return new ResponseEntity<>(Classes.getClassesRus(), HttpStatus.OK);
    }

    @GetMapping("/dnd5/attributes/en/basic")
    public ResponseEntity<List<Attribute>> getAttributesEn() {
        return new ResponseEntity<>(attributesService.getBasicAttributesDnd5Eng(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/attributes/ru/basic")
    public ResponseEntity<List<Attribute>> getAttributesRu() {
        return new ResponseEntity<>(attributesService.getBasicAttributesDnd5Rus(), HttpStatus.OK);
    }

    @PostMapping("/dnd5/ru/character/basic")
    public ResponseEntity<Character> createBasicCharacter(@RequestParam(name = "user_id") long user_id, @RequestBody Character character) {
        character.setSlots(new Spells_slots());
        Optional<Character> optionalCharacter = characterService.createCharacter(user_id,character);
        if (optionalCharacter.isPresent()) {
            Character characterCur = optionalCharacter.get();
            long id = characterCur.getId();
            List<Attribute> attributeList = attributesService.createAttributes(id,attributesService.getBasicAttributesDnd5Rus());
            List<Skill> skillsList = skillsService.setBasicSkillsDnd5Rus(attributeList);
//            characterService.updateCharacter(character);
            if(attributeList == null || skillsList == null) {
                characterService.deleteCharacter(characterCur.getId());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(optionalCharacter.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




    @PutMapping("/dnd5/ru/character/step2")
    public ResponseEntity<Character> addRaceAndClass(@RequestParam long character_id, @RequestParam String race, @RequestParam String cclass) {
        if (characterService.addRaceAndClass(character_id, race, cclass)) {
            Optional<Character> character = characterService.getCharacter(character_id);
            return character.isPresent()? new ResponseEntity<>(character.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
