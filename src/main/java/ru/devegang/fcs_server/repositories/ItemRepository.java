package ru.devegang.fcs_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.fcs_server.entities.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
