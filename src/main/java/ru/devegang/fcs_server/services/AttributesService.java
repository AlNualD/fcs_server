package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.additional.dnd5.Attributes;
import ru.devegang.fcs_server.entities.Attribute;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.repositories.AttributeRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AttributesService implements AttributesServiceInterface {
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private CharacterService characterService;

    private boolean checkAttribute(Attribute attribute) {
        return !attribute.getName().isBlank()&&attribute.getAmount()>0;
    }

    @Override
    public Optional<Attribute> createAttribute(long character_id, Attribute attribute) {
        Optional<Character> character = characterService.getCharacter(character_id);
        if(checkAttribute(attribute) && character.isPresent()) {
            attribute.setModification();
            attribute.setCharacter(character.get());
            return Optional.of(attributeRepository.saveAndFlush(attribute));
        }
        return Optional.empty();
    }

    @Override
    public List<Attribute> createAttributes(long character_id, List<Attribute> attributes) {
        Optional<Character> character = characterService.getCharacter(character_id);
        if(character.isPresent()) {
            for (Attribute attribute : attributes) {
                if(checkAttribute(attribute)) {
                    attribute.setCharacter(character.get());
                    attribute.setModification();
                } else {
                    return null;
                }
            }
            List<Attribute> newAttributes = attributeRepository.saveAll(attributes);
            attributeRepository.flush();
            return newAttributes;
        }
        return null;
    }

    private boolean isExist(long id){
        return attributeRepository.existsById(id);
    }

    @Override
    public boolean deleteAttribute(long id) {
        attributeRepository.deleteById(id);
        return isExist(id);
    }

    @Override
    public boolean updateAttribute(Attribute attribute) {
            Optional<Attribute> optionalAttribute = getAttribute(attribute.getId());
            if(optionalAttribute.isPresent()) {
            Attribute oldAttr = optionalAttribute.get();
            oldAttr.setName(attribute.getName());
            oldAttr.setAmount(attribute.getAmount());
            oldAttr.setTrainedSaveRoll(attribute.isTrainedSaveRoll());
            if(!checkAttribute(oldAttr)) return false;
            attributeRepository.saveAndFlush(oldAttr);
            return true;
            }

        return false;
    }

    private List<Attribute> makeAttributes(List<String> attributesNames) {
        List<Attribute> attributes = new LinkedList<>();
        for (String name : attributesNames) {
            Attribute attribute = new Attribute();
            attribute.setName(name);
            attribute.setAmount(10);
            attribute.setModification();
            attribute.setId(-1);
            attributes.add(attribute);
        }
        return attributes;
    }

    @Override
    public List<Attribute> getBasicAttributesDnd5Eng() {
        return makeAttributes(Attributes.getAttributesEng());
    }

    @Override
    public List<Attribute> getBasicAttributesDnd5Rus() {
        return makeAttributes(Attributes.getAttributesRus());
    }

    public Optional<Attribute> getAttribute(long attribute_id) {
        return attributeRepository.findById(attribute_id);
    }

    public List<Attribute> getCharacterAttribute(long character_id) {
        Optional<Character> optionalCharacter = characterService.getCharacter(character_id);
        return optionalCharacter.isPresent()? optionalCharacter.get().getAttributes() : List.of();
    }
}
