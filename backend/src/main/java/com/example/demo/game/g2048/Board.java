package com.example.demo.game.g2048;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.example.demo.game.BoardGame;
import com.example.demo.game.BoardPiece;
import com.example.demo.game.Piece;
import com.example.demo.core.Direction;
import com.example.demo.core.GameState;

@JsonIgnoreProperties(value = {"pieces", "score"}, allowGetters = true)
public class Board implements BoardGame {
    @JsonProperty("cells")
    private final int[][] values = new int[4][4];
    @JsonProperty("prev")
    private Board prev;
    @JsonProperty("failed")
    private boolean failed = false;
    @JsonProperty("lastDir")
    private Direction lastDir = Direction.UP;
    private final Random random = new Random();

    public Board() {
        spawnPiece(lastDir);
        spawnPiece(lastDir);
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    public Board(
        @JsonProperty("cells") int[][] cells,
        @JsonProperty("prev") Board prev,
        @JsonProperty("failed") Boolean failed,
        @JsonProperty("lastDir") Direction lastDir) {
        if (cells != null) {
            for (int i = 0; i < 4 && i < cells.length; i++) {
                System.arraycopy(cells[i], 0, this.values[i], 0, Math.min(cells[i].length, 4));
            }
        }
        this.prev = prev;
        this.failed = failed != null && failed;
        this.lastDir = lastDir != null ? lastDir : Direction.UP;
    }

    public int get(int x, int y) {
        return values[x][y];
    }

    @Override
    @JsonProperty("cells")
    public int[][] getCells() {
        return values;
    }

    @JsonProperty("prev")
    public Board getPrev() { return prev; }

    @JsonProperty("failed")
    public boolean getFailed() { return failed; }

    @JsonProperty("lastDir")
    public Direction getLastDir() { return lastDir; }

    @Override
    @JsonProperty("pieces")
    public List<BoardPiece> getPieces() {
        List<BoardPiece> pieces = new ArrayList<>();
        int id = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int val = values[x][y];
                if (val != 0) {
                    pieces.add(new Piece(id++, String.valueOf(val), 1, 1, x, y));
                }
            }
        }
        return pieces;
    }

    @Override
    @JsonProperty("score")
    public int getScore() {
        int max = 0;
         for (int[] row : values) {
            for (int v : row) max = Math.max(max, v);
        }
        return max;
    }

    @Override
    public boolean move(Integer pieceId, Direction dir) {
        if (dir == Direction.BACK) {
            if (this.prev == null) return false;
            Board previousBoard = this.prev;
            for (int i = 0; i < 4; i++) {
                System.arraycopy(previousBoard.values[i], 0, this.values[i], 0, 4);
            }
            this.failed = previousBoard.failed;
            this.lastDir = previousBoard.lastDir;
            this.prev = previousBoard.prev;
            return true;
        }

        Board previous = this.copy();
        int[][] before = copyGrid();
        switch (dir) {
           case LEFT -> slide(false, false);
            case RIGHT -> slide(true, false);
            case UP -> slide(false, true);
            case DOWN -> slide(true, true);
            default -> {
            }
        }
        boolean changed = !Arrays.deepEquals(before, values);
        this.prev = previous;
        boolean spawned = spawnPiece(dir);
        if (!spawned) {
            this.failed = true;
        }
       this.lastDir = dir;
       return changed || spawned;
    }
   

    private void slide(boolean reverse, boolean vertical) {
        for (int i = 0; i < 4; i++) {
            int[] line = new int[4];
            for (int j = 0; j < 4; j++) {
                int val = vertical ? values[j][i] : values[i][j];
                line[reverse ? 3 - j : j] = val;
            }
            int[] processed = processLine(line);
            for (int j = 0; j < 4; j++) {
                if (vertical) values[j][i] = processed[reverse ? 3 - j : j];
                else values[i][j] = processed[reverse ? 3 - j : j];
            }
        }
    }

    private static int[] processLine(int[] line) {
        List<Integer> result = new ArrayList<>();
         for (int v : line) if (v != 0) result.add(v);
        boolean merged;
        do {
            merged = false;
            for (int i = 0; i < result.size() - 1; i++) {
                if (result.get(i).equals(result.get(i + 1))) {
                    result.set(i, result.get(i) * 2);
                    result.remove(i + 1);
                    merged = true;
                }
            }
        } while (merged);
        while (result.size() < 4) result.add(0);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private int[][] copyGrid() {
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(values[i], 0, copy[i], 0, 4);
        }
        return copy;
    }

    @Override
    @JsonIgnore
    public boolean isSolved() {
       for (int[] row : values) {
            for (int v : row) if (v == 2048) return true;
        }
        return false;
    }


    @Override
    @JsonIgnore
    public boolean isFailed() {
        return failed;
    }
    
    public boolean spawnPiece(Direction dir) {
        Direction edge = dir.opposite();
        List<int[]> candidates = new ArrayList<>();
        switch (edge) {
            case LEFT -> {
                for (int y = 0; y < 4; y++) if (values[0][y] == 0) candidates.add(new int[]{0, y});
            }
            case RIGHT -> {
                for (int y = 0; y < 4; y++) if (values[3][y] == 0) candidates.add(new int[]{3, y});
            }
            case UP -> {
                for (int x = 0; x < 4; x++) if (values[x][0] == 0) candidates.add(new int[]{x, 0});
            }
            case DOWN -> {
                for (int x = 0; x < 4; x++) if (values[x][3] == 0) candidates.add(new int[]{x, 3});
            }
            case BACK -> {}
        }
        if (candidates.isEmpty()) return false;
        int[] pos = candidates.get(random.nextInt(candidates.size()));
        values[pos[0]][pos[1]] = random.nextDouble() < 0.9 ? 2 : 4;
        return true;
    }
   
    private Board(boolean dummy) {}

    public Board copy() {
        Board copy = new Board(false);
        for (int i = 0; i < 4; i++) {
            System.arraycopy(this.values[i], 0, copy.values[i], 0, 4);
        }
        copy.prev = this.prev != null ? this.prev.copy() : null;
        copy.failed = this.failed;
        copy.lastDir = this.lastDir;
        return copy;
    }
   

    public static Board fromJson(String json) {
        return GameState.fromJson(json, Board.class);
    }

    @JsonIgnore
    public int[][] getValues() {
        return values;
    }
}
