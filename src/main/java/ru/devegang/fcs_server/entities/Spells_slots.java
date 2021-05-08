package ru.devegang.fcs_server.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "spells_slots")
public class Spells_slots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    long id;

    @OneToOne(mappedBy = "slots")
    private Character character;

    @Column(name = "lvl1")
    int lvl1 = 0;
    @Column(name = "lvl2")
    int lvl2 = 0;
    @Column(name = "lvl3")
    int lvl3 = 0;
    @Column(name = "lvl4")
    int lvl4 = 0;
    @Column(name = "lvl5")
    int lvl5 = 0;
    @Column(name = "lvl6")
    int lvl6 = 0;
    @Column(name = "lvl7")
    int lvl7 = 0;
    @Column(name = "lvl8")
    int lvl8 = 0;
    @Column(name = "lvl9")
    int lvl9 = 0;
}
