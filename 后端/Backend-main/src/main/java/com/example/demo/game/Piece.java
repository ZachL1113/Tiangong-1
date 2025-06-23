package com.example.demo.game;

import com.example.demo.core.Direction;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Piece(
    @JsonProperty("id") int id,
    @JsonProperty("name") String name,
    @JsonProperty("w") @JsonAlias("width") int width,
    @JsonProperty("h") @JsonAlias("height") int height,
    @JsonProperty("x") int x,
    @JsonProperty("y") int y
) implements BoardPiece {

    @Override public int getId() { return id; }
    @Override public String getName() { return name; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }

    public Piece moved(Direction dir) {
        int nx = x + (dir == Direction.RIGHT ? 1 : dir == Direction.LEFT ? -1 : 0);
        int ny = y + (dir == Direction.DOWN ? 1 : dir == Direction.UP ? -1 : 0);
        return new Piece(id, name, width, height, nx, ny);
    }

    public boolean occupies(int px, int py) {
        return px >= x && px < x + width && py >= y && py < y + height;
    }
}

