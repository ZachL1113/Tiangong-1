package com.example.demo;

import com.example.demo.core.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    @Test
    void testFromInputValid() {
        assertEquals(Direction.UP, Direction.fromInput("W"));
        assertEquals(Direction.DOWN, Direction.fromInput("s"));
        assertEquals(Direction.LEFT, Direction.fromInput("A"));
        assertEquals(Direction.RIGHT, Direction.fromInput("d"));
    }

    @Test
    void testFromInputInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Direction.fromInput("x"));
    }

    @Test
    void testFromInputNull() {
        assertThrows(IllegalArgumentException.class, () -> Direction.fromInput(null));
    }
}
