package ru.devegang.fcs_server.additional.dnd5;

import java.util.List;

public enum Attributes {
    Strength(0), Constitution(1), Dexterity(2), Intelligence(3), Wisdom(4), Charisma(5);

    private int index;

    Attributes(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    static public List<String> getAttributesEng() {
        return List.of( "Strength",
                "Constitution",
                "Dexterity",
                "Intelligence",
                "Wisdom",
                "Charisma");
    }

    static public List<String> getAttributesRus() {
        return List.of("Сила",
                "Телосложение",
                "Ловкость",
                "Интеллект",
                "Мудрость",
                "Харизма");
    }
}
