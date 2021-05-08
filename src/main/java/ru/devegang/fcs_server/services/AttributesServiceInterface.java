package ru.devegang.fcs_server.services;

import ru.devegang.fcs_server.entities.Attribute;

import java.util.List;
import java.util.Optional;

public interface AttributesServiceInterface {
    public Optional<Attribute> createAttribute(long character_id, Attribute attribute);
    public List<Attribute> createAttributes(long character_id, List<Attribute> attributes);
    public boolean deleteAttribute(long id);
    public boolean updateAttribute(Attribute attribute);
    public List<Attribute> getBasicAttributesDnd5Eng();
    public List<Attribute> getBasicAttributesDnd5Rus();
}
