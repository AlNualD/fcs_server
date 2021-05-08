package ru.devegang.fcs_server.additional;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class RollingFormula implements Serializable {
    //[1]d[20][+-1]


    RollingMode mode;
    private int dicesAmount;
    private int dice;
    private int modification;

    @Override
    public String toString() {
        return "{FORMULA " + mode.toString() + " " + dicesAmount + "d"+dice + " " + modification +"}";
    }

//    public static RollingFormula getFromString(String formula) {
//
//    }
}
