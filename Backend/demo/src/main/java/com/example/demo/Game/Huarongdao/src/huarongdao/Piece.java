package com.example.demo.Game.Huarongdao.src.huarongdao;

public class Piece {
    private final int id;
    private final String name;
    private final int width;
    private final int height;
    private int x;
    private int y;

    public Piece(int id, String name, int width, int height, int x, int y) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getX() { return x; }
    public int getY() { return y; }

    void move(Direction dir) {
        switch (dir) {
            case UP:    y -= 1; break;
            case DOWN:  y += 1; break;
            case LEFT:  x -= 1; break;
            case RIGHT: x += 1; break;
        }
    }

    boolean occupies(int px, int py) {
        return px >= x && px < x + width && py >= y && py < y + height;
    }

    Piece copy() {
        return new Piece(id, name, width, height, x, y);
    }
}