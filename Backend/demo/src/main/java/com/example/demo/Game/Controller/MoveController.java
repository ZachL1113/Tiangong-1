package com.example.demo.Game.Controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.Game.Game2048.src.Board;
import com.example.demo.Game.Game2048.src.Direction;
import com.example.demo.Level.LevelService;


@RestController
@CrossOrigin
@RequestMapping("/games")
public class MoveController {

    private final LevelService levels;

    public MoveController(LevelService levels) {
        this.levels = levels;
    }

    @GetMapping("/2048/new")
    public Board new2048() {
        return new Board();
    }

    @PostMapping("/2048/move")
    public MoveResponse move2048(@RequestBody MoveRequest req) {
        Board board = Board.fromJson(req.boardJson);
        Direction dir = parseDir(req.direction);
        boolean valid = board.move(dir);
        if (valid) {
            board.spawnTile();
        }
        return new MoveResponse(board.toJson(), valid, board.isSucceeded());
    }

    @GetMapping("/huarongdao/{index}")
    public String newHuarongdao(@PathVariable int index) {
        var board = levels.huarongdaoBoard(index);
        return board != null ? board.toJson() : null;
    }

    @PostMapping("/huarongdao/move")
    public MoveResponse moveHuarongdao(@RequestBody HuarongRequest req) {
        com.example.demo.Game.Huarongdao.src.huarongdao.Board board =
                com.example.demo.Game.Huarongdao.src.huarongdao.Board.fromJson(req.boardJson);
        com.example.demo.Game.Huarongdao.src.huarongdao.Direction dir = parseHrdDir(req.direction);
        boolean valid = board.movePiece(req.pieceId, dir);
        return new MoveResponse(board.toJson(), valid, board.isSolved());
    }

    private com.example.demo.Game.Huarongdao.src.huarongdao.Direction parseHrdDir(String in) {
        switch (in.toUpperCase()) {
            case "W": return com.example.demo.Game.Huarongdao.src.huarongdao.Direction.UP;
            case "S": return com.example.demo.Game.Huarongdao.src.huarongdao.Direction.DOWN;
            case "A": return com.example.demo.Game.Huarongdao.src.huarongdao.Direction.LEFT;
            case "D": return com.example.demo.Game.Huarongdao.src.huarongdao.Direction.RIGHT;
            default: throw new IllegalArgumentException("Unknown direction: " + in);
        }
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

    public static class MoveRequest {
        public String boardJson;
        public String direction;
    }

    public static class HuarongRequest extends MoveRequest {
        public int pieceId;
    }

    public static class MoveResponse {
        public String boardJson;
        public boolean valid;
        public boolean success;

        public MoveResponse(String json, boolean valid, boolean success) {
            this.boardJson = json;
            this.valid = valid;
            this.success = success;
        }
    }
}