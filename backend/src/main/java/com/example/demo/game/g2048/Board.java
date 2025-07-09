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
    private final Random random = new Random();

    public Board() {
        spawnPiece(Direction.UP);
        spawnPiece(Direction.UP);
    }

    @com.fasterxml.jackson.annotation.JsonCreator
        public Board(@com.fasterxml.jackson.annotation.JsonProperty("cells") int[][] cells,
                 @com.fasterxml.jackson.annotation.JsonProperty("prev") Board prev) {
        for (int i = 0; i < 4 && cells != null && i < cells.length; i++) {
            System.arraycopy(cells[i], 0, this.values[i], 0, Math.min(cells[i].length, 4));
        }
        this.prev = prev;
    }

    public int get(int x, int y) {
        return values[x][y];
    }

    @JsonProperty("cells")
    public int[][] getCells() {
        return values;
    }

    @JsonProperty("prev")
    public Board getPrev() { return prev; }

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

    @JsonProperty("score")
    public int getScore() {
        int max = 0;
        for (int[] row : values)
            for (int x : row)
                max = Math.max(max, x);
        return max;
    }
    public boolean move(Integer pieceId, Direction dir) {
        if (dir == Direction.BACK) {
            if (this.prev == null) return false;
            for (int i = 0; i < 4; i++)
                System.arraycopy(this.prev.values[i], 0, values[i], 0, 4);
            this.prev = this.prev.prev;
            return true;
        }

        Board previous = this.copy();
        int[][] before = copyGrid();
        switch (dir) {
            case LEFT:  slide(false, false); break;
            case RIGHT: slide(true, false); break;
            case UP:    slide(false, true);  break;
            case DOWN:  slide(true, true);   break;
            default:    break;
        }
        boolean changed = !Arrays.deepEquals(before, values);
        if (changed) {
            this.prev = previous;
            spawnPiece(dir);
        }
        return changed;
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
        for (int x : line) if (x != 0) result.add(x);
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i).equals(result.get(i + 1))) {
                result.set(i, result.get(i) * 2);
                result.remove(i + 1);
            }
        }
        while (result.size() < 4) result.add(0);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private int[][] copyGrid() {
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++) System.arraycopy(values[i], 0, copy[i], 0, 4);
        return copy;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isSolved() {
        for (int[] row : values)
            for (int val : row)
                if (val == 2048) return true;
        return false;
    }


    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isFailed() {
        for (Direction d : Direction.values()) {
            Board copy = this.copy();
            if (copy.move(d)) return false;
        }
        return true;
    }
    
    public void spawnPiece(Direction dir) {
        List<int[]> candidates = new ArrayList<>();
        switch (dir) {
            case LEFT -> {
                for (int y = 0; y < 4; y++) if (values[3][y] == 0) candidates.add(new int[]{3, y});
            }
            case RIGHT -> {
                for (int y = 0; y < 4; y++) if (values[0][y] == 0) candidates.add(new int[]{0, y});
            }
            case UP -> {
                for (int x = 0; x < 4; x++) if (values[x][3] == 0) candidates.add(new int[]{x, 3});
            }
            case DOWN -> {
                for (int x = 0; x < 4; x++) if (values[x][0] == 0) candidates.add(new int[]{x, 0});
            }
            default -> {}
        }
        if (candidates.isEmpty()) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                     if (values[x][y] == 0) {
                        candidates.add(new int[]{x, y});
                    }
                }
            }
        }
            
        if (!candidates.isEmpty()) {
            int[] pos = candidates.get(random.nextInt(candidates.size()));
            values[pos[0]][pos[1]] = random.nextDouble() < 0.9 ? 2 : 4;
        }
    }
   
    private Board(boolean dummy) {}

    public Board copy() {
        Board copy = new Board(false);
        for (int i = 0; i < 4; i++) {
            System.arraycopy(this.values[i], 0, copy.values[i], 0, 4);
        }
        copy.prev = this.prev != null ? this.prev.copy() : null;
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
