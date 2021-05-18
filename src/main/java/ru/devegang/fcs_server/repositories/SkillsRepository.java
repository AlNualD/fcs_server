package ru.devegang.fcs_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.fcs_server.entities.Skill;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skill,Long> {
}
