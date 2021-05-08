package ru.devegang.fcs_server.additional.dnd5;

import java.util.List;

public enum Attributes {
    Strength, Dexterity, Wisdom, Charisma, Intelligence, Constitution;

    static public List<String> getAttributesEng() {
        return List.of("Strength", "Dexterity", "Wisdom", "Charisma", "Intelligence", "Constitution");
    }
    static public List<String> getAttributesRus() {
        return List.of("Сила","Телосложение", "Ловкость", "Мудрость", "Интеллект", "Харизма");
    }
}
