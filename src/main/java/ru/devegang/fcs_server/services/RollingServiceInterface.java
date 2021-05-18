package ru.devegang.fcs_server.services;

import ru.devegang.fcs_server.additional.RollingFormula;
import ru.devegang.fcs_server.additional.RollingResult;

public interface RollingServiceInterface {
    public RollingResult regularRoll(RollingFormula formula);
    public RollingResult regularRoll(String formula);
}
