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
    private final Random random = new Random();

    public Board() {
        spawnPiece();
        spawnPiece();
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    public Board(@com.fasterxml.jackson.annotation.JsonProperty("cells") int[][] cells) {
        for (int i = 0; i < 4 && cells != null && i < cells.length; i++) {
            System.arraycopy(cells[i], 0, this.values[i], 0, Math.min(cells[i].length, 4));
        }
    }

    public int get(int x, int y) {
        return values[x][y];
    }

    @JsonProperty("cells")
    public int[][] getCells() {
        return values;
    }

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
        int[][] before = copyGrid();
        switch (dir) {
            case LEFT:  slide(false, false); break;
            case RIGHT: slide(true, false); break;
            case UP:    slide(false, true);  break;
            case DOWN:  slide(true, true);   break;
        }
        return !Arrays.deepEquals(before, values);
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

    public void spawnPiece() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (values[i][j] == 0)
                    empty.add(new int[]{i, j});
        if (!empty.isEmpty()) {
            int[] pos = empty.get(random.nextInt(empty.size()));
            values[pos[0]][pos[1]] = random.nextDouble() < 0.9 ? 2 : 4;
        }
    }
     

    private Board(boolean dummy) {}

    public Board copy() {
        Board copy = new Board(false);
        for (int i = 0; i < 4; i++) {
            System.arraycopy(this.values[i], 0, copy.values[i], 0, 4);
        }
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
