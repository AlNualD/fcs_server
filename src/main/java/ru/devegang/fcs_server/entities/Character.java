package ru.devegang.fcs_server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.devegang.fcs_server.additional.dnd5.Attributes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

//    @Column(name = "user_id")
//    private long user_id;

    @Column(name = "name")
    String name;

    @Column(name = "class")
    String classC;
    @Column(name = "race")
    String race;
    @Column(name = "lvl")
    int lvl;
    @Column(name = "healthDice")
    int healthDice = 1;
    @Column(name = "hp_max")
    int hp_max;
    @Column(name = "hp_cur")
    int hp_cur;
    @Column(name = "alignment")
    String alignment;
    @Column(name = "spells_total")
    int spells_total;
    @Column(name = "money")
    double money;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    String description;



    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany( mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @JsonIgnore
    @OneToMany( mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Spell> spells = new ArrayList<>();
    @JsonIgnore
    @OneToMany( mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> inventory = new ArrayList<>();

    @JsonIgnore
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "spells_slots_id")
    private Spells_slots slots;

    @JsonIgnore
    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Attribute> attributes = new ArrayList<>();


//
//    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinTable(name = "users")
////    @JoinColumn (name = "users_id", referencedColumnName = "id")
//    private User user;

    public int getProfBonus(){
        return ((this.lvl - 1) % 4) + 2;
    }

    public void firstLvlHp(int dice) {
        healthDice = dice;
        hp_max = dice + attributes.get(Attributes.Constitution.getIndex()).getModification();
    }


    @Override
    public String toString() {
        //return super.toString();
        return "{character " + id + " uid " + user.getId() + " name:" + name + "}";
    }
}
