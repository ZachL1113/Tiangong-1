package com.example.demo.game.huarongdao;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.game.BoardGame;
import com.example.demo.game.BoardPiece;
import com.example.demo.game.Piece;
import com.example.demo.core.Direction;
import com.example.demo.core.GameState;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board implements BoardGame {
    public static final int ROWS = 5, COLS = 4;
    private final List<Piece> pieces = new ArrayList<>();
    @JsonProperty("prev")
    private Board prev;

    public Board() {}

    @com.fasterxml.jackson.annotation.JsonCreator
    public Board(@com.fasterxml.jackson.annotation.JsonProperty("pieces") java.util.List<Piece> pieces,
                 @com.fasterxml.jackson.annotation.JsonProperty("prev") Board prev) {
        if (pieces != null) {
            this.pieces.addAll(pieces);
        }
        this.prev = prev;
    }

    @JsonProperty("pieces")
    public List<BoardPiece> getPieces() { return new ArrayList<>(pieces); }
    @JsonProperty("prev")
    public Board getPrev() { return prev; }
    public void addPiece(Piece p) { pieces.add(p); }
    public Piece getPiece(int id) {
        return pieces.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @JsonProperty("cells")
    public int[][] getCells() {
        int[][] grid = new int[COLS][ROWS];
        for (Piece p : pieces) {
            for (int x = p.getX(); x < p.getX() + p.getWidth(); x++) {
                for (int y = p.getY(); y < p.getY() + p.getHeight(); y++) {
                    grid[x][y] = p.getId();
                }
            }
        }
        return grid;
    }

    @JsonProperty("score")
    public int getScore() {
        return 0;
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

    public boolean move(Integer pieceId, Direction dir) {
        if (dir == Direction.BACK) {
            if (this.prev == null) return false;
            this.pieces.clear();
            this.prev.pieces.forEach(this.pieces::add);
            this.prev = this.prev.prev;
            return true;
        }
        if (pieceId == null) return false;
        Piece p = getPiece(pieceId);
        if (p != null && canMove(p, dir)) {
            Board previous = this.copy();
            int idx = pieces.indexOf(p);
            pieces.set(idx, p.moved(dir));
            this.prev = previous;
            return true;
        }
        return false;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isSolved() {
        Piece king = getPiece(9);
        return king != null && king.getX() == 1 && king.getY() == 3;
    }

    public Board copy() {
        Board b = new Board();
        pieces.forEach(b::addPiece);
        b.prev = this.prev != null ? this.prev.copy() : null;
        return b;
    }

    public static Board fromJson(String json) {
        return GameState.fromJson(json, Board.class);
    }
}
