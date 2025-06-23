package com.example.demo.core;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;
    public static Direction fromInput(String in) {
        if (in == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        return switch (in.toUpperCase()) {
            case "W" -> UP;
            case "S" -> DOWN;
            case "A" -> LEFT;
            case "D" -> RIGHT;
            default -> throw new IllegalArgumentException("Unknown direction: " + in);
        };
    }
}
