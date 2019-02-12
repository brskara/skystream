package com.sky.stream.enums;

import java.util.Arrays;

public enum ParentalControlLevel {
    LEVEL_U("U", 0),
    LEVEL_PG("PG", 1),
    LEVEL_12("12", 2),
    LEVEL_15("15", 3),
    LEVEL_18("18", 4);

    private String name;
    private int value;

    ParentalControlLevel(final String name, final int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ParentalControlLevel fromName(String name) {
        return Arrays.stream(ParentalControlLevel.values())
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid parental control level: %s", name)));
    }

}
