package com.example.demo.game.service;

import org.springframework.stereotype.Service;

import com.example.demo.core.Direction;
import com.example.demo.game.huarongdao.Board;
import com.example.demo.level.LevelService;
import com.example.demo.game.service.dto.MoveRequest;
import com.example.demo.game.service.dto.MoveResponse;

@Service("huarongdao")
public class HuarongdaoService implements GameService {

    private final LevelService levels;

    public HuarongdaoService(LevelService levels) {
        this.levels = levels;
    }

    @Override
    public String newGame(Integer level) {
        int idx = level != null ? level : 0;
        Board board = levels.huarongdaoBoard(idx);
        return board != null ? board.toJson() : null;
    }

    @Override
    public MoveResponse move(MoveRequest req) {
        Board board = Board.fromJson(req.boardJson());
        Direction dir = Direction.fromInput(req.direction());
        boolean valid = board.move(req.pieceId(), dir);

        System.out.println("👉 After move boardJson: " + board.toJson());

        return new MoveResponse(board.toJson(), valid, board.isSolved());
    }

}
