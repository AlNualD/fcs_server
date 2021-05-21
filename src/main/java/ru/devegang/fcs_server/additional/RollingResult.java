package ru.devegang.fcs_server.additional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@AllArgsConstructor
public class RollingResult {
    int result;
    LinkedList<Integer> rolls;
    public  RollingResult() {
        rolls = new LinkedList<>();
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void addRoll(int roll) {
        this.rolls.addLast(roll);
    }
}
