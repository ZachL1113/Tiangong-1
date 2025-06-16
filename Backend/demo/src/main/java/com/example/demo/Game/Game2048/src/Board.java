package com.example.demo.Game.Game2048.src;

import java.util.*;

import com.example.demo.Core.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Board implements GameState{
    private final int[][] values = new int[4][4];
    private final Random random = new Random();

    public Board() {
        spawnTile();
        spawnTile();
    }

    public int get(int x, int y) {
        return values[x][y];
    }

    public boolean move(Direction dir) {
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

    public boolean isSucceeded() {
        for (int[] row : values)
            for (int val : row)
                if (val == 2048) return true;
        return false;
    }

    public boolean isFailed() {
        for (Direction d : Direction.values()) {
            Board copy = new Board(false);
            for (int i = 0; i < 4; i++)
                System.arraycopy(values[i], 0, copy.values[i], 0, 4);
            if (copy.move(d)) return false;
        }
        return true;
    }

    public void spawnTile() {
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

    public int getScore() {
        int max = 0;
        for (int[] row : values)
            for (int x : row)
                max = Math.max(max, x);
        return max;
    }

    private Board(boolean dummy) {}

    @Override
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Board fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, Board.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int[][] getValues() {
        return values;
    }

}
