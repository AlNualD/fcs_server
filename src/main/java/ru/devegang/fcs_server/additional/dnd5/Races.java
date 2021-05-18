package ru.devegang.fcs_server.additional.dnd5;

import java.util.LinkedList;
import java.util.List;

public enum Races {
    Dwarf,
    Human,
    Elf,
    Halfling,
    Dragonborn,
    Gnome,
    Halfelf,
    Halforc,
    Tiefling;


    public static Races getRace(String race) {
        switch (race) {
            case "Дварф":
            case "Dwarf": return Dwarf;
            case "Человек":
            case "Human":return Human;
            case "Эльф":
            case "Elf": return Elf;
            case "Полурослик":
            case "Halfling": return Halfling;
            case "Драконорожденный":
            case "Dragonborn": return Dragonborn;
            case "Гном":
            case "Gnome" : return Gnome;
            case "Полуэльф":
            case "Halfelf":return Halfelf;
            case "Полуорк":
            case "Halforc": return Halforc;
            case "Тифлинг":
            case "Tiefling": return Tiefling;
            default: throw new IllegalArgumentException("No such race " + race);
        }
    }

    public static List<String> getRacesEng() {
       return List.of("Dwarf",
               "Human",
               "Elf",
               "Halfling",
               "Dragonborn",
               "Gnome",
               "Halfelf",
               "Halforc",
               "Tiefling");
    }
    public static List<String> getRacesRus() {
        return List.of("Дварф", "Человек", "Эльф", "Полурослик", "Драконорожденный", "Гном", "Полуэльф", "Полуорк", "Тифлинг");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
