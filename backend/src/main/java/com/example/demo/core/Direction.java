package com.example.demo.core;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, BACK;
    
    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case BACK -> BACK;
        };
    }
    
    public static Direction fromInput(String in) {
        if (in == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        return switch (in.toUpperCase()) {
            case "U" -> UP;
            case "D" -> DOWN;
            case "L" -> LEFT;
            case "R" -> RIGHT;
            case "B" -> BACK;
            default -> throw new IllegalArgumentException("Unknown direction: " + in);
        };
    }
}
