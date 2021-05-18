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
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;


    @Column(name = "train")
    int trainCoefficient;  //-1 -- its trait, 0 -- just skill, 1 -- trained, 2 -- specialisation

    @Column(name = "canBeTrained")
    boolean canBeTrained = false;

    @JsonIgnore
    @ManyToOne
    private Attribute attribute;


//    TODO add db func to auto upgrade
    @Column(name = "value")
    int value;


    public boolean isTrait() {
        return this.trainCoefficient < 0;
    }

    @Column(name = "name")
    String name;
    @Column(name = "definition")
    String definition;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    String description;

}
