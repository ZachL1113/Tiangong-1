package com.example.demo;

import com.example.demo.core.GameState;
import com.example.demo.core.GameStateFactory;
import com.example.demo.game.g2048.Board;
import com.example.demo.game.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSerializationTest {

    @Test
    void test2048BoardSerialization() {
        GameStateFactory.register("2048", Board.class);
        Board board = new Board();
        String json = board.toJson();
        Board copy = Board.fromJson(json);
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(board.getValues()[i], copy.getValues()[i]);
        }

        String json2 = GameStateFactory.toJson(board);
        GameState state = GameStateFactory.fromJson("2048", json2);
        assertTrue(state instanceof Board);
        Board copy2 = (Board) state;
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(board.getValues()[i], copy2.getValues()[i]);
        }
    }

    @Test
    void testHuarongdaoBoardSerialization() {
        GameStateFactory.register("huarongdao", com.example.demo.game.huarongdao.Board.class);
        com.example.demo.game.huarongdao.Board board = new com.example.demo.game.huarongdao.Board();
        board.addPiece(new Piece(0, "a", 1, 1, 0, 0));
        board.addPiece(new Piece(1, "b", 1, 2, 1, 0));
        String json = board.toJson();
        com.example.demo.game.huarongdao.Board copy = com.example.demo.game.huarongdao.Board.fromJson(json);
        assertEquals(board.getPieces().size(), copy.getPieces().size());

        String json2 = GameStateFactory.toJson(board);
        GameState state = GameStateFactory.fromJson("huarongdao", json2);
        assertTrue(state instanceof com.example.demo.game.huarongdao.Board);
    }
}
