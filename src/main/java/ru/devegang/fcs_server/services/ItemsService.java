package ru.devegang.fcs_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.devegang.fcs_server.entities.Character;
import ru.devegang.fcs_server.entities.Item;
import ru.devegang.fcs_server.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

public class ItemsService implements ItemsServiceInterface {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CharacterService characterService;
    @Override
    public Optional<Item> getItem(long id) {
        return itemRepository.findById(id);
    }

    private boolean checkItem(Item item) {
        return item.getWeight()>=0 && !item.getName().isBlank();
    }

    @Override
    public Optional<Item> createItem(long character_id, Item item) {
        Optional<Character> character = characterService.getCharacter(character_id);
        if(character.isPresent()&&checkItem(item)) {
            item.setCharacter(character.get());
            return Optional.of(itemRepository.saveAndFlush(item));
        }
        return Optional.empty();
    }

    private boolean isExist(long id) {
        return itemRepository.existsById(id);
    }

    @Override
    public boolean deleteItem(long id) {
        itemRepository.deleteById(id);
        return !isExist(id);
    }

    @Override
    public boolean updateItem(Item item) {
        if(isExist(item.getId())&&checkItem(item)) {
            itemRepository.saveAndFlush(item);
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getItems(long character_id) {
        Optional<Character> character = characterService.getCharacter(character_id);
        if(character.isPresent()) {
            return character.get().getInventory();
        }
        return null;
    }
}
