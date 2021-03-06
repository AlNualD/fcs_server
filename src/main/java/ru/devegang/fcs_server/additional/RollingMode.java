package ru.devegang.fcs_server.additional;

public enum RollingMode {
    DISADVANTAGE(-1),
    NORMAL(0),
    ADVANTAGE(1);

    private int i;

    RollingMode(int i) {
    this.i = i;
    }


    @Override
    public String toString() {
        return String.valueOf(this.i);
    }

    public static RollingMode getFromInt(int i) {
        for (RollingMode value : values()) {
            if(value.i == i) return value;
        }
        return null;
    }
}
