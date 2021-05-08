package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.Skill;
import ru.devegang.fcs_server.repositories.SkillsRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SkillsService implements  SkillsServiceInterface {

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private CharacterService characterService;

    private boolean checkSkill(Skill skill) {
        return !skill.getName().isBlank();
    }

    private  boolean isExists(long id) {
        return skillsRepository.existsById(id);
    }

    @Override
    public Optional<Skill> createSkill(long character_id, Skill skill) {
        Optional<Character> characterOptional =  characterService.getCharacter(character_id);
        if(characterOptional.isPresent() && checkSkill(skill)) {
            return  Optional.of(skillsRepository.saveAndFlush(skill));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Skill> getSkill(long id) {
        return skillsRepository.findById(id);
    }

    @Override
    public List<Skill> getSkills() {
        return skillsRepository.findAll();
    }

    @Override
    public List<Skill> getCharacterSkills(long character_id) {
        Optional<Character> character = characterService.getCharacter(character_id);
        if(character.isPresent()) {
            return character.get().getSkills();
        }
        return null;
    }

    @Override
    public List<Skill> getUserSkills(long user_id) {
        List<Skill> skills = new LinkedList();
        List<Character> characters = characterService
                .getUserCharacters(user_id);
        if(characters == null) {
            return null;
        }
        for (Character character : characters) {
            skills.addAll(character.getSkills());
        }
        return skills;
    }


    @Override
    public boolean deleteSkill(long id) {
        skillsRepository.deleteById(id);
        return !isExists(id);
    }

    @Override
    public boolean updateSkill(Skill skill) {
        if(checkSkill(skill) && isExists(skill.getId())) {
            skillsRepository.saveAndFlush(skill);
            return true;
        }
        return false;
    }
}
