package ru.devegang.fcs_server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "spells")
public class Spell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;



    @Column(name = "name")
    String name;
    @Column(name = "definition")
    String definition;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    String description;
    @Column(name = "formula")
    String formula;
    @Column(name = "lvl")
    Byte lvl = -1;
    @Column(name = "favorite")
    Boolean favorite = false;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToOne
    private Attribute attribute;
    @Column(name = "difficulty")
    int difficulty = -1;

    public void updateDifficulty() {
        if(this.getAttribute() != null) {
            difficulty = 8 + attribute.getModification() + character.getProfBonus();
        }
    }
}
