package com.example.demo.Game.Huarongdao.src.huarongdao;

import java.util.Scanner;

/**
 * Simple console game implementation for HuaRongDao.
 * Use numbers 0-11 for pieces and WASD (or UDLR) for directions.
 */
public class Game {
    private Board board;
    private Board initial;
    private final java.util.Deque<Board> history = new java.util.ArrayDeque<>();
    private final Scanner scanner = new Scanner(System.in);
    private int steps = 0;

    public Game(Board board) {
        this.board = board;
        this.initial = board.copy();
        history.push(board.copy());
        steps = 0;
    }

    private void printIntro() {
        System.out.println(
                "Welcome to HuaRong Dao! Move piece 9 to the exit.\n" +
                "Commands: <id> <directions>, B=undo, R=reset, C=level, Q=quit.\n" +
                "Use W A S D letters and you may enter several at once.\n");
    }

    private static final int CELL_W = 6;
    private static final int CELL_H = 3;

    /**
     * Determine if a character should be treated as double width
     * when rendering to the console. This is a simplistic check
     * that handles common CJK characters.
     */
    private static boolean isWide(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block)
            || Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(block)
            || Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(block);
    }

    /**
     * Compute the display width of a string, counting CJK characters as
     * two columns. This helps keep names inside their boxes.
     */
    private static int displayWidth(String text) {
        int w = 0;
        for (int i = 0; i < text.length(); i++) {
            w += isWide(text.charAt(i)) ? 2 : 1;
        }
        return w;
    }

    /**
     * Write text into the grid respecting double width characters.
     */
    private static void writeText(char[][] grid, int row, int col, int maxWidth, String text) {
        int used = 0;
        int pos = col;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int w = isWide(c) ? 2 : 1;
            if (used + w > maxWidth) break;
            grid[row][pos] = c;
            if (w == 2) {
                // occupy the next cell with a space so borders stay aligned
                if (pos + 1 < grid[row].length) {
                    grid[row][pos + 1] = ' ';
                }
            }
            pos += w;
            used += w;
        }
    }

    /**
     * Print the board using boxes with piece names inside.
     */
    private void printBoard() {
        int width = Board.COLS * CELL_W;
        int height = Board.ROWS * CELL_H;
        char[][] grid = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ' ';
            }
        }

        for (Piece p : board.getPieces()) {
            int sx = p.getX() * CELL_W;
            int sy = p.getY() * CELL_H;
            int w = p.getWidth() * CELL_W;
            int h = p.getHeight() * CELL_H;
            int ex = sx + w - 1;
            int ey = sy + h - 1;

            // draw horizontal borders
            grid[sy][sx] = '┌';
            grid[sy][ex] = '┐';
            grid[ey][sx] = '└';
            grid[ey][ex] = '┘';
            for (int x = sx + 1; x < ex; x++) {
                grid[sy][x] = '─';
                grid[ey][x] = '─';
            }

            // draw vertical borders
            for (int y = sy + 1; y < ey; y++) {
                grid[y][sx] = '│';
                grid[y][ex] = '│';
            }

            // place name at the middle line taking double width chars into account
            String name = p.getName();
            int innerW = w - 2;
            int nameRow = sy + h / 2;
            int nameWidth = displayWidth(name);
            int nameStart = sx + 1 + Math.max(0, (innerW - nameWidth) / 2);
            writeText(grid, nameRow, nameStart, innerW, name);
        }

        for (char[] row : grid) {
            System.out.println(new String(row));
        }

        // draw exit indicator below the board with side lines
        char[] exitRow = new char[width];
        java.util.Arrays.fill(exitRow, ' ');
        int left = CELL_W;
        int right = CELL_W * 3 - 1;
        exitRow[left] = '│';
        exitRow[right] = '│';
        String exit = "EXIT";
        int exitStart = left + 1 + (right - left - 1 - displayWidth(exit)) / 2;
        char[][] tmp = { exitRow };
        writeText(tmp, 0, exitStart, right - left - 1, exit);
        System.out.println(new String(exitRow));
    }

    private Direction parseDir(String in) {
        switch (in.toUpperCase()) {
            case "W":
                return Direction.UP;
            case "S":
                return Direction.DOWN;
            case "A":
                return Direction.LEFT;
            case "D":
                return Direction.RIGHT;
            default:
                throw new IllegalArgumentException("Unknown direction: " + in);
        }
    }

    private void selectLevel() {
        java.util.List<Board> levels = com.example.demo.Level.HuarongdaoDefaults.boards();
        System.out.println("Select level (0-" + (levels.size() - 1) + ", Q=exit):");
        for (int i = 0; i < levels.size(); i++) {
            System.out.println(i + ": level " + i);
        }
        while (true) {
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("Q")) System.exit(0);
            try {
                int idx = Integer.parseInt(in);
                if (idx >= 0 && idx < levels.size()) {
                    board = levels.get(idx).copy();
                    initial = board.copy();
                    history.clear();
                    history.push(board.copy());
                    steps = 0;
                    return;
                }
            } catch (Exception ignore) {}
        }
    }

    public void play() {
        printIntro();
        selectLevel();
        while (true) {
            printBoard();
            if (board.isSolved()) {
                System.out.println("Congratulations! Solved in " + steps + " moves.");
                selectLevel();
                continue;
            }
            System.out.print("Move (e.g. '9 d', B=back, R=reset, C=level, Q=exit): ");
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("Q")) break;
            if (line.equalsIgnoreCase("B")) {
                if (history.size() > 1) {
                    history.pop();
                    board = history.peek().copy();
                    if (steps > 0) steps--;
                }
                continue;
            }
            if (line.equalsIgnoreCase("R")) {
                board = initial.copy();
                history.clear();
                history.push(board.copy());
                steps = 0;
                continue;
            }
            if (line.equalsIgnoreCase("C")) {
                selectLevel();
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid input!");
                continue;
            }
            try {
                int id = Integer.parseInt(parts[0]);
                String seq = parts[1];
                for (int i = 0; i < seq.length(); i++) {
                    Direction dir = parseDir(String.valueOf(seq.charAt(i)));
                    if (board.movePiece(id, dir)) {
                        history.push(board.copy());
                        steps++;
                    } else {
                        System.out.println("Invalid move!");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game(com.example.demo.Level.HuarongdaoDefaults.boards().get(0));
        game.play();
    }
}