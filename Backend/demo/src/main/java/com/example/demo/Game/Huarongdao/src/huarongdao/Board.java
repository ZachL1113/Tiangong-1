package com.example.demo.Game.Huarongdao.src.huarongdao;

import java.util.*;

import com.example.demo.Core.GameState;
import com.example.demo.Core.Direction;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Board implements GameState{
    public static final int ROWS = 5, COLS = 4;
    private final List<Piece> pieces = new ArrayList<>();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Board() {}

    public List<Piece> getPieces() { return pieces; }
    public void addPiece(Piece p) { pieces.add(p); }
    public Piece getPiece(int id) {
        return pieces.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < COLS && y < ROWS;
    }

    private boolean isFree(int x, int y, Piece ignore) {
        if (!isInside(x, y)) return false;
        return pieces.stream().allMatch(p -> p == ignore || !p.occupies(x, y));
    }

    public boolean canMove(Piece piece, Direction dir) {
        int dx = dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0;
        int dy = dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0;
        for (int x = piece.getX(); x < piece.getX() + piece.getWidth(); x++) {
            for (int y = piece.getY(); y < piece.getY() + piece.getHeight(); y++) {
                if (!isFree(x + dx, y + dy, piece)) return false;
            }
        }
        return true;
    }

    public boolean movePiece(int id, Direction dir) {
        Piece p = getPiece(id);
        if (p != null && canMove(p, dir)) {
            p.move(dir);
            return true;
        }
        return false;
    }

    public boolean isSolved() {
        Piece king = getPiece(9);
        return king != null && king.getX() == 1 && king.getY() == 3;
    }

    public Board copy() {
        Board b = new Board();
        pieces.forEach(p -> b.addPiece(p.copy()));
        return b;
    }

    public static Board fromArrays(int[][] pos, int[][] def) {
        Board b = new Board();
        for (int i = 0; i < Math.min(pos.length, def.length); i++) {
            b.addPiece(new Piece(i, String.valueOf(i), def[i][0], def[i][1], pos[i][0], pos[i][1]));
        }
        return b;
    }

    @Override
    public String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Board fromJson(String json) {
        try {
            return MAPPER.readValue(json, Board.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
