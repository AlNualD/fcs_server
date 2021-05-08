package ru.devegang.fcs_server.services;

import ru.devegang.fcs_server.entities.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillsServiceInterface {
    Optional<Skill> createSkill(long character_id, Skill skill);
    Optional<Skill> getSkill(long id);
    List<Skill> getSkills();
    List<Skill> getCharacterSkills(long character_id);
    List<Skill> getUserSkills(long user_id);
    boolean deleteSkill(long id);
    boolean updateSkill(Skill skill);

}
