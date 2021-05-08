package ru.devegang.fcs_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.devegang.fcs_server.additional.dnd5.Classes;
import ru.devegang.fcs_server.additional.dnd5.Races;
import ru.devegang.fcs_server.services.CharacterService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DND5Controller {

    @Autowired
    CharacterService characterService;


    @GetMapping("/dnd5/races/en")
    public ResponseEntity<List<String>> getRacesEn() {
        return new ResponseEntity<List<String>>(Races.getRacesEng(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/races/ru")
    public ResponseEntity<List<String>> getRacesRu() {
        return new ResponseEntity<List<String>>(Races.getRacesRus(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/classes/en")
    public ResponseEntity<List<String>> getClassesEn() {
        return new ResponseEntity<List<String>>(Classes.getClassesEng(), HttpStatus.OK);
    }
    @GetMapping("/dnd5/classes/ru")
    public ResponseEntity<List<String>> getClassesRu() {
        return new ResponseEntity<List<String>>(Classes.getClassesRus(), HttpStatus.OK);
    }



}
