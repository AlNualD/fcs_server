package ru.devegang.fcs_server.services;

import ru.devegang.fcs_server.entities.Item;

import java.util.List;
import java.util.Optional;

public interface ItemsServiceInterface {
    Optional<Item> getItem(long id);
    Optional<Item> createItem(long character_id, Item item);
    boolean deleteItem(long id);
    boolean updateItem(Item item);
    List<Item> getItems(long character_id);
}
