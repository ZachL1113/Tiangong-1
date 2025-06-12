package huarongdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    public static final int ROWS = 5;
    public static final int COLS = 4;

    private final List<Piece> pieces = new ArrayList<>();

    public Board() {}

    public List<Piece> getPieces() {
        return pieces;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public Piece getPiece(int id) {
        return pieces.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < COLS && y < ROWS;
    }

    private boolean isFree(int x, int y, Piece ignore) {
        if (!isInside(x, y)) return false;
        for (Piece p : pieces) {
            if (p != ignore && p.occupies(x, y)) return false;
        }
        return true;
    }

    public boolean canMove(Piece piece, Direction dir) {
        switch (dir) {
            case UP:
                for (int px = piece.getX(); px < piece.getX() + piece.getWidth(); px++) {
                    if (!isFree(px, piece.getY() - 1, piece)) return false;
                }
                break;
            case DOWN:
                for (int px = piece.getX(); px < piece.getX() + piece.getWidth(); px++) {
                    if (!isFree(px, piece.getY() + piece.getHeight(), piece)) return false;
                }
                break;
            case LEFT:
                for (int py = piece.getY(); py < piece.getY() + piece.getHeight(); py++) {
                    if (!isFree(piece.getX() - 1, py, piece)) return false;
                }
                break;
            case RIGHT:
                for (int py = piece.getY(); py < piece.getY() + piece.getHeight(); py++) {
                    if (!isFree(piece.getX() + piece.getWidth(), py, piece)) return false;
                }
                break;
        }
        return true;
    }

    public boolean movePiece(int id, Direction dir) {
        Piece p = getPiece(id);
        if (p == null) return false;
        if (!canMove(p, dir)) return false;
        p.move(dir);
        return true;
    }

    public boolean isSolved() {
        Piece king = getPiece(9); // Cao Cao is id 9 in default board
        return king != null && king.getX() == 1 && king.getY() == 3;
    }

    public Board copy() {
        Board b = new Board();
        for (Piece p : pieces) {
            b.addPiece(p.copy());
        }
        return b;
    }

    // Build default board using state3 from the original Python version
    public static Board defaultBoard() {
        Board b = new Board();
        int[][] defs = {
            {0,2,1,1,2}, {1,1,1,1,3}, {2,1,1,2,3}, {3,1,1,0,4}, {4,1,1,3,4},
            {5,1,2,0,0}, {6,1,2,3,0}, {7,1,2,0,2}, {8,1,2,3,2}, {9,2,2,1,0}
        };
        for (int[] d : defs) {
            b.addPiece(new Piece(d[0], String.valueOf(d[0]), d[1], d[2], d[3], d[4]));
        }
        return b;
    }

    // Build the four preset levels from the original Python version
    public static List<Board> levels() {
        return Arrays.asList(level0(), level1(), level2(), level3());
    }

    private static Board level0() {
        // positions listed as {x, y}
        int[][] pos = {
            {2,0}, {2,3}, {3,3}, {2,4}, {3,4}, {2,2}, {0,1}, {1,1}, {0,0}, {0,3}, {2,1}, {3,1}
        };
        int[][] def = {
            {2,1}, {1,1}, {1,1}, {1,1}, {1,1}, {2,1}, {1,2}, {1,2}, {2,1}, {2,2}, {1,1}, {1,1}
        };
        return build(pos, def);
    }

    private static Board level1() {
        int[][] pos = {
            {0,0}, {0,4}, {1,4}, {2,4}, {3,4}, {3,0}, {0,2}, {1,0}, {1,2}, {2,2}, {2,0}, {2,1}
        };
        int[][] def = {
            {1,2}, {1,1}, {1,1}, {1,1}, {1,1}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {1,1}, {1,1}
        };
        return build(pos, def);
    }

    private static Board level2() {
        int[][] pos = {
            {1,0}, {0,0}, {3,0}, {1,3}, {2,3}, {3,1}, {0,3}, {0,1}, {3,3}, {1,1}, {1,4}, {2,4}
        };
        int[][] def = {
            {2,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {1,1}, {1,1}
        };
        return build(pos, def);
    }

    private static Board level3() {
        int[][] pos = {
            {1,2}, {1,3}, {2,3}, {0,4}, {3,4}, {0,0}, {3,0}, {0,2}, {3,2}, {1,0}, {1,4}, {2,4}
        };
        int[][] def = {
            {2,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {1,1}, {1,1}
        };
        return build(pos, def);
    }

    private static Board build(int[][] pos, int[][] def) {
        Board b = new Board();
        for (int i = 0; i < pos.length && i < 10; i++) {
            b.addPiece(new Piece(i, String.valueOf(i), def[i][0], def[i][1], pos[i][0], pos[i][1]));
        }
        return b;
    }
}