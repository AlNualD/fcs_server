package ru.devegang.fcs_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.fcs_server.entities.Spell;

public interface SpellRepository extends JpaRepository<Spell, Long> {
}
