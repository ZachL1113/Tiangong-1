package com.example.demo.Level;

import java.util.*;
import com.example.demo.Game.Huarongdao.src.huarongdao.Board;
import com.example.demo.Game.Huarongdao.src.huarongdao.Piece;

public class HuarongdaoDefaults {
    public static List<Board> boards() {
        return Arrays.asList(level0(), level1(), level2(), level3());
    }

    private static Board level0() {
        return build(
            new int[][]{{2,0}, {2,3}, {3,3}, {2,4}, {3,4}, {2,2}, {0,1}, {1,1}, {0,0}, {0,3}, {2,1}, {3,1}},
            new int[][]{{2,1}, {1,1}, {1,1}, {1,1}, {1,1}, {2,1}, {1,2}, {1,2}, {2,1}, {2,2}, {1,1}, {1,1}}
        );
    }

    private static Board level1() {
        return build(
            new int[][]{{0,0}, {0,4}, {1,4}, {2,4}, {3,4}, {3,0}, {0,2}, {1,0}, {1,2}, {2,2}, {2,0}, {2,1}},
            new int[][]{{1,2}, {1,1}, {1,1}, {1,1}, {1,1}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {1,1}, {1,1}}
        );
    }

    private static Board level2() {
        return build(
            new int[][]{{1,0}, {0,0}, {3,0}, {1,3}, {2,3}, {3,1}, {0,3}, {0,1}, {3,3}, {1,1}, {1,4}, {2,4}},
            new int[][]{{2,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {1,1}, {1,1}}
        );
    }

    private static Board level3() {
        return build(
            new int[][]{{1,2}, {1,3}, {2,3}, {0,4}, {3,4}, {0,0}, {3,0}, {0,2}, {3,2}, {1,0}, {1,4}, {2,4}},
            new int[][]{{2,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {1,1}, {1,1}}
        );
    }

    private static Board build(int[][] pos, int[][] def) {
        Board b = new Board();
        for (int i = 0; i < Math.min(pos.length, def.length); i++) {
            b.addPiece(new Piece(i, String.valueOf(i), def[i][0], def[i][1], pos[i][0], pos[i][1]));
        }
        return b;
    }
}