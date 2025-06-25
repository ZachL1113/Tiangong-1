package com.example.demo.game.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.game.service.GameService;
import com.example.demo.game.service.dto.MoveRequest;
import com.example.demo.game.service.dto.MoveResponse;

@RestController
@CrossOrigin
@RequestMapping("/games")
public class MoveController {

    private final Map<String, GameService> services;

    public MoveController(Map<String, GameService> services) {
        this.services = services;
    }

    @GetMapping("/{game}/new")
    public String newGame(@PathVariable String game, @RequestParam(required = false) Integer level) {
        GameService svc = services.get(game);
        if (svc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown game: " + game);
        }
        return svc.newGame(level);
    }

    @PostMapping("/{game}/move")
    public MoveResponse move(@PathVariable String game, @RequestBody MoveRequest req) {
        GameService svc = services.get(game);
        if (svc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown game: " + game);
        }
        return svc.move(req);
    }
}
