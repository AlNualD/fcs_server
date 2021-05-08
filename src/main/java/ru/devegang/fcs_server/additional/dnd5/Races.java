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
    Halfork,
    Tiefling;

    public static List<String> getRacesEng() {
       return List.of("Dwarf",
               "Human",
               "Elf",
               "Halfling",
               "Dragonborn",
               "Gnome",
               "Half-elf",
               "Half-ork",
               "Tiefling");
    }
    public static List<String> getRacesRus() {
        return List.of("Дварф", "Человек", "Эльф", "Полурослик", "Драконорожденный", "Гном", "Полуэльф", "Полуорк", "Тифлинг");
    }

}
