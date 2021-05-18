package ru.devegang.fcs_server.additional.dnd5;

import java.util.List;

public enum Skills {
    Athletics(0), Acrobatics(1), SleightOfHand(2), Stealth(3), Arcana(4), History(5), Investigation(6), Nature(7), Religion(8),
    AnimalHandling(9), Insight(10), Medicine(11), Perception(12), Survival(13), Deception(14), Intimidation(15), Performance(16), Persuasion(17);


    final static List<String> skillsRus = List.of("Атлетика",
            "Акробатика",
            "Ловкость рук",
            "Скрытность",
            "Магия",
            "История",
            "Расследование",
            "Природа",
            "Религия",
            "Уход за животными",
            "Проницательность",
            "Медицина",
            "Внимание",
            "Выживание",
            "Обман",
            "Угроза",
            "Выступление",
            "Убеждение");


    final static List<String> skillsEng = List.of("Athletics",
            "Acrobatics",
            "Sleight Of Hand",
            "Stealth",
            "Arcana",
            "History",
            "Investigation",
            "Nature",
            "Religion",
            "Animal Handling",
            "Insight",
            "Medicine",
            "Perception",
            "Survival",
            "Deception",
            "Intimidation",
            "Performance",
            "Persuasion");

    int index;
    Skills(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getNameRus() {
        return skillsRus.get(this.index);
    }

    public String getNameEng() {
        return skillsEng.get(this.index);
    }

    public Attributes getAttribute() {
        switch (this) {
            case Athletics: return Attributes.Strength;
            case Acrobatics:
            case SleightOfHand:
            case Stealth: return Attributes.Dexterity;
            case Arcana:
            case History:
            case Investigation:
            case Nature:
            case Religion: return Attributes.Intelligence;
            case AnimalHandling:
            case Insight:
            case Medicine:
            case Perception:
            case Survival: return Attributes.Wisdom;
            case Deception:
            case Intimidation:
            case Performance:
            case Persuasion: return Attributes.Charisma;
            default: return null;
        }
    }


    public  static List<String> getSkillsEng() {
        return skillsEng;
    }

    public  static List<String> getSkillsRus() {
        return skillsRus;
    }

//    public static List<Skills> getSkills() {
//        return List.of(Skills.Athletics,Skills.SleightOfHand,    Skills.Athletics, Skills.Acrobatics, Skills.SleightOfHand, Skills.Stealth, Skills.Arcana, Skills.History, Skills.Investigation, Skills.Nature, Skills.Religion,
//                Skills.AnimalHandling, Skills.Insight, Skills.Medicine, Skills.Perception, Skills.Survival, Skills.Deception, Skills.Intimidation, Skills.Performance, Skills.Persuasion);
//    }



}

//Strength
//
//    Athletics
//
//Dexterity
//
//    Acrobatics
//    Sleight of Hand
//    Stealth
//
//Intelligence
//
//    Arcana
//    History
//    Investigation
//    Nature
//    Religion
//
//Wisdom
//
//    Animal Handling
//    Insight
//    Medicine
//    Perception
//    Survival
//
//Charisma
//
//    Deception
//    Intimidation
//    Performance
//    Persuasion
