package com.example.demo.core;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;
    public static Direction fromInput(String in) {
        if (in == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        return switch (in.toUpperCase()) {
            case "U" -> UP;
            case "D" -> DOWN;
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> throw new IllegalArgumentException("Unknown direction: " + in);
        };
    }
}
