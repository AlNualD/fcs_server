package ru.devegang.fcs_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.User;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {


}
