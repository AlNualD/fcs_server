package ru.devegang.fcs_server.services;

import org.springframework.stereotype.Service;
import ru.devegang.fcs_server.additional.RollingFormula;
import ru.devegang.fcs_server.additional.RollingMode;
import ru.devegang.fcs_server.additional.RollingResult;

@Service
public class RollingService implements RollingServiceInterface {

    private int generator(int dice) {
        return (int) (Math.random() * dice) + 1;
    }

    private int rolling(int dice, RollingMode mode) {
        int first = generator(dice);
        int second = generator(dice);
        switch (mode) {
            case NORMAL: return first;
            case ADVANTAGE:
                return Math.max(first, second);
            case DISADVANTAGE:
                return Math.min(first, second);
            default: return -1;
        }
    }

    @Override
    public RollingResult regularRoll(RollingFormula formula) {
        RollingResult rollingResult = new RollingResult();
        int sum = 0;
        for (int i = 0; i < formula.getDicesAmount(); i++) {
            int result =  rolling(formula.getDice(),formula.getMode());
            rollingResult.addRoll(result);
            sum += result;

        }
        sum += formula.getModification();
        rollingResult.setResult(sum);
        return rollingResult;
    }

    @Override
    public RollingResult regularRoll(String formula) {
        RollingFormula rollingFormula = RollingFormula.gerRollingFormula(formula);
        return rollingFormula!=null? regularRoll(rollingFormula) : null;
    }


}
