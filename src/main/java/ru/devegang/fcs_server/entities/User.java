package ru.devegang.fcs_server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;
    @Column(name = "name")
    String name;
    @Column(name = "login", unique = true)
    String login;


    @Column(name = "password")
    private String password;



    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "character_count")
    int character_count;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters = new ArrayList<>();


    @Override
    public String toString() {
        return "{user " + id + " " + name + "}";
    }


}
