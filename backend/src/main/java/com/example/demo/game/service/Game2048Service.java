package com.example.demo.game.service;

import org.springframework.stereotype.Service;

import com.example.demo.core.Direction;
import com.example.demo.game.g2048.*;
import com.example.demo.game.service.dto.MoveRequest;
import com.example.demo.game.service.dto.MoveResponse;

@Service("2048")
public class Game2048Service implements GameService {

    @Override
    public String newGame(Integer level) {
        return new Board().toJson();
    }

    @Override
    public MoveResponse move(MoveRequest req) {
        Board board = Board.fromJson(req.boardJson());
        Direction dir = Direction.fromInput(req.direction());
        boolean valid = board.move(req.pieceId(), dir);
        int endState = board.isSolved() ? 1 : board.isFailed() ? -1 : 0;
        int endState = board.isSolved() ? 1 : board.isFailed() ? -1 : 0;
        return new MoveResponse(board.toJson(), valid, board.isSolved(), endState);
    }

}
