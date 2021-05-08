package ru.devegang.fcs_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.fcs_server.entities.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute,Long> {
}
