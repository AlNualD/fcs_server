package ru.devegang.fcs_server.additional;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return mode.toString() + ";" + dicesAmount + ";"+dice + ";" + modification;
    }

    public static boolean checkStringFormula(String formula) {
        Pattern pattern = Pattern.compile("^(0|-?1);\\d+;\\d+;-?\\d+");
        Matcher matcher = pattern.matcher(formula);
        return matcher.matches();
    }

    public static RollingFormula gerRollingFormula(String formula) {
        if (checkStringFormula(formula)) {
            String[] values = formula.split(";");
            return new RollingFormula(RollingMode.getFromInt(Integer.parseInt(values[0])),Integer.parseInt(values[1]),Integer.parseInt(values[2]),Integer.parseInt(values[3]));
        }
        return null;
    }
    

    

//    public static RollingFormula getFromString(String formula) {
//
//    }
}
