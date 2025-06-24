package com.example.demo.game.service;

import com.example.demo.game.service.dto.MoveRequest;
import com.example.demo.game.service.dto.MoveResponse;

public interface GameService {
    String newGame(Integer level);
    MoveResponse move(MoveRequest req);
}
