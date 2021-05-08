package ru.devegang.fcs_server.additional.dnd5;

import java.util.List;

public enum Classes {
    Barbarian,
    Bard,
    Cleric,
    Druid,
    Fighter,
    Paladin,
    Ranger,
    Rouge,
    Sorcerer,
    Warlock,
    Wizard;

    public static List<String> getClassesEng() {
        return List.of("Barbarian" +
                "Bard" +
                "Cleric" +
                "Druid" +
                "Fighter" +
                "Paladin" +
                "Ranger" +
                "Rouge" +
                "Sorcerer" +
                "Warlock" +
                "Wizard");
    }

    public  static List<String> getClassesRus() {
        return List.of("Варвар", "Бард", "Жрец", "Друид", "Воин", "Паладин", "Следопыт", "Плут", "Чародей", "Колдун", "Волшебник");
    }


}
