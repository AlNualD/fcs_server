package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.additional.dnd5.Skills;
import ru.devegang.fcs_server.entities.Attribute;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.Skill;
import ru.devegang.fcs_server.repositories.SkillsRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class SkillsService implements  SkillsServiceInterface {

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private AttributesService attributesService;

    public boolean deleteDependencies(long attribute_id) {
        Optional<Attribute> attribute = attributesService.getAttribute(attribute_id);
        if(attribute.isPresent()) return false;
        List<Skill> skills = skillsRepository.findAllByAttribute(attribute.get());
        for (Skill skill : skills) {
            skill.setAttribute(null);
            skillsRepository.saveAndFlush(skill);
        }
        return true;
    }

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
            skill.setCharacter(characterOptional.get());
            return  Optional.of(skillsRepository.saveAndFlush(skill));
        }
        return Optional.empty();
    }

    public Optional<Skill> createSkill(Character character, Attribute attribute, Skill skill) {
        if(checkSkill(skill)) {
            skill.setCharacter(character);
            skill.setAttribute(attribute);
            return Optional.of(skillsRepository.saveAndFlush(skill));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Skill> createSkill(long character_id, Attribute attribute, Skill skill) {
        Optional<Character> characterOptional =  characterService.getCharacter(character_id);
        if(characterOptional.isPresent()) {
            createSkill(characterOptional.get(),attribute,skill);
        }
        return Optional.empty();
    }

    public Optional<Skill> createSkill(long character_id, long attribute_id, Skill skill) {
        Optional<Attribute> attributeOptional = attributesService.getAttribute(attribute_id);
        return attributeOptional.isPresent()? createSkill(character_id,attributeOptional.get(),skill) : Optional.empty();

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

    public boolean updateSkillsByAttribute(long attribute_id) {
        Optional<Attribute> optionalAttribute = attributesService.getAttribute(attribute_id);

        if(optionalAttribute.isPresent()){
            List<Skill> skills = skillsRepository.findAllByAttribute(optionalAttribute.get());
            for (Skill skill : skills) {
                skill.updateModification();
            }
            skillsRepository.saveAll(skills);
            skillsRepository.flush();
        }

        return false;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        Optional<Skill> optionalSkill = getSkill(skill.getId());
        if(optionalSkill.isPresent()) {
            Skill old = optionalSkill.get();
            old.setDefinition(skill.getDefinition());
            old.setDescription(skill.getDescription());
            old.setName(skill.getName());
            old.setTrainCoefficient(skill.getTrainCoefficient());
            old.setCanBeTrained(skill.isCanBeTrained());
            if(skill.getValue() != -1 ) {
                old.setValue(skill.getValue());
            } else {
                old.updateModification();
            }
            skillsRepository.saveAndFlush(old);
            return true;
        }
        return false;
    }



    @Override
    public List<Skill> setBasicSkillsDnd5Rus(long character_id) {
        Optional<Character> character = characterService.getCharacter(character_id);
        return character.isPresent() ? setBasicSkillsDnd5Rus(character.get().getAttributes()) : List.of();
    }

    private void setSkillInf(Attribute attribute, Skill skill) {
        Character character = attribute.getCharacter();
        skill.setCharacter(character);
        skill.setAttribute(attribute);
        skill.setTrainCoefficient(0);
        skill.setValue(attribute.getModification());
    }

    @Override
    public List<Skill> setBasicSkillsDnd5Rus(List<Attribute> attributes) {
        if(attributes.isEmpty()) return null;
        List<Skill> skills = new LinkedList<>();
        for (Skills value : Skills.values()) {
            Skill skill = new Skill();
            skill.setName(value.getNameRus());
            Attribute attribute = attributes.get(value.getAttribute().getIndex());
            setSkillInf(attribute, skill);
            skills.add(skill);
        }
        List<Skill> newSkills = skillsRepository.saveAll(skills);
        skillsRepository.flush();
        return skills;
    }

    @Override
    public List<Skill> setBasicSkillsDnd5Eng(long character_id) {
        Optional<Character> character = characterService.getCharacter(character_id);
        return character.isPresent() ? setBasicSkillsDnd5Eng(character.get().getAttributes()) : List.of();     }

    @Override
    public List<Skill> setBasicSkillsDnd5Eng(List<Attribute> attributes) {
        List<Skill> skills = new LinkedList<>();
        for (Skills value : Skills.values()) {
            Skill skill = new Skill();
            skill.setName(value.getNameEng());
            Attribute attribute = attributes.get(value.getAttribute().getIndex());
            setSkillInf(attribute, skill);
            skills.add(skill);
        }
        return skills;
    }
}
