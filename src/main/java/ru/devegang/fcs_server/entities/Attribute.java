package ru.devegang.fcs_server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @Column(name = "name")
    private String name;


    @Column(name = "amount")
    private int amount;

    @Column(name = "modif")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int modification;

    @Column(name = "is_trained")
    private boolean isTrainedSaveRoll;

    public void setAmount(int amount) {
        this.amount = amount;
        setModification();
    }

    public void incrementAmount() {
        this.amount++;
        setModification();
    }
    public void setModification() {
        this.modification = (this.amount / 2) - 5;
    }
}
