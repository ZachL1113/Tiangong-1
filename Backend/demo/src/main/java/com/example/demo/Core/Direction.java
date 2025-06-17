package com.example.demo.Core;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction fromLetter(String in) {
        switch (in.toUpperCase()) {
            case "W": return UP;
            case "S": return DOWN;
            case "A": return LEFT;
            case "D": return RIGHT;
            default: throw new IllegalArgumentException("Unknown direction: " + in);
        }
    }
}
