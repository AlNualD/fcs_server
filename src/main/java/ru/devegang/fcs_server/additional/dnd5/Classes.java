package ru.devegang.fcs_server.additional.dnd5;

import java.util.List;

public enum Classes {
    Barbarian,
    Bard,
    Cleric,
    Druid,
    Fighter,
    Monk,
    Paladin,
    Ranger,
    Rouge,
    Sorcerer,
    Warlock,
    Wizard;

    public static Classes getClass(String cclass) {
        switch (cclass) {
            case "Варвар":
            case "Barbarian": return Barbarian;
            case "Бард":
            case "Bard": return  Bard;
            case "Жрец":
            case "Cleric": return Cleric;
            case "Друид":
            case "Druid": return Druid;
            case "Воин":
            case "Fighter": return Fighter;
            case "Monk":
            case "Монах": return Monk;
            case "Паладин":
            case "Paladin": return Paladin;
            case "Следопыт":
            case "Ranger" : return Ranger;
            case "Плут":
            case "Rouge": return Rouge;
            case "Чародей":
            case "Sorcerer": return Sorcerer;
            case "Колдун":
            case "Warlock": return Warlock;
            case "Волшебник":
            case "Wizard": return Wizard;
            default: throw new IllegalArgumentException("No such class "+cclass);
        }
    }

    public static List<String> getClassesEng() {
        return List.of("Barbarian",
                "Bard" ,
                "Cleric" ,
                "Druid" ,
                "Fighter" ,
                "Monk" ,
                "Paladin" ,
                "Ranger" ,
                "Rouge" ,
                "Sorcerer" ,
                "Warlock" ,
                "Wizard");
    }

    public  static List<String> getClassesRus() {
        return List.of("Варвар", "Бард", "Жрец", "Друид", "Воин", "Монах", "Паладин", "Следопыт", "Плут", "Чародей", "Колдун", "Волшебник");
    }


}
