package com.example.demo.game;

import com.example.demo.core.Direction;
import com.example.demo.core.GameState;
import java.util.List;

public interface BoardGame extends GameState {
    int[][] getCells();
    List<BoardPiece> getPieces();
    int getScore();
    boolean move(Integer pieceId, Direction dir);

    boolean isSolved();

    default boolean move(Direction dir) {
        return move(null, dir);
    }
}
