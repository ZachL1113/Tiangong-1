package com.example.demo.Game.Game2048.src;

import java.util.Scanner;

public class Game {
    private Board board;
    private final Scanner scanner = new Scanner(System.in);

    public Game(Board board) {
        this.board = board;
    }

    private void printIntro() {
        System.out.println(
            "Welcome to 2048! Synthesize numbers to reach 2048!\n" +
            "Controls: W (↑), A (←), S (↓), D (→)\n" +
            "Other: R = reset game, Q = quit\n"
        );
    }

    private void printBoard() {
        int cellW = 5;
        String lineSep = "+-----+-----+-----+-----+";
        System.out.println(lineSep);
        for (int r = 0; r < 4; r++) {
            StringBuilder row = new StringBuilder();
            for (int c = 0; c < 4; c++) {
                int val = board.get(r, c);
                String cell = val == 0 ? "" : Integer.toString(val);
                int pad = cellW - cell.length();
                int left = pad / 2, right = pad - left;
                row.append("|").append(" ".repeat(left)).append(cell).append(" ".repeat(right));
            }
            row.append("|");
            System.out.println(row);
            System.out.println(lineSep);
        }
    }

    private void reset() {
        this.board = new Board();
        printBoard();
    }

    private Direction parseDir(String in) {
        switch (in.toUpperCase()) {
            case "W": return Direction.UP;
            case "S": return Direction.DOWN;
            case "A": return Direction.LEFT;
            case "D": return Direction.RIGHT;
            default: throw new IllegalArgumentException("Unknown direction: " + in);
        }
    }

    public void play() {
        printIntro();
        reset();
        while (true) {
            if (board.isSucceeded()) {
                System.out.println("You made 2048! You win! Score: " + board.getScore());
                break;
            }

            if (board.isFailed()) {
                System.out.println("No more moves. Game over! Max tile: " + board.getScore());
                System.out.print("Restart? (Y/N): ");
                String ans = scanner.nextLine().trim();
                if (ans.equalsIgnoreCase("Y")) {
                    reset();
                    continue;
                } else break;
            }

            System.out.print("Your move (W/A/S/D, R=reset, Q=quit): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Q")) break;
            if (input.equalsIgnoreCase("R")) {
                reset();
                continue;
            }

            try {
                Direction dir = parseDir(input);
                if (board.move(dir)) {
                    board.spawnTile();
                    printBoard();
                } else {
                    System.out.println("SOS! Move not possible in that direction.");
                }
            } catch (Exception e) {
                System.out.println("SOS! Invalid input. Use W/A/S/D, R, or Q.");
            }
        }
    }

    public static void main(String[] args) {
        new Game(new Board()).play();
    }
}
