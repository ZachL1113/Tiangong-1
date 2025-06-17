package com.example.demo.State;

import com.example.demo.Player.Player;
import com.example.demo.Core.GameState;
import com.example.demo.Core.GameStateFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/situations")
public class StateController {

    private final StateRepository repo;

    public StateController(StateRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public State add(@RequestBody SituationDTO dto) {
        State s = new State();
        s.setGame(dto.game);
        s.setLevel(dto.level);
        s.setPlayer(dto.player);
        s.setSituationJson(GameStateFactory.toJson(dto.board));
        return repo.save(s);
    }

    @GetMapping
    public List<State> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public GameState getBoard(@PathVariable Long id) {
        State s = repo.findById(id).orElseThrow();
        return GameStateFactory.fromJson(s.getGame(), s.getSituationJson());
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @RequestBody SituationDTO dto) {
        State s = repo.findById(id).orElseThrow();
        s.setGame(dto.game);
        s.setLevel(dto.level);
        s.setPlayer(dto.player);
        s.setSituationJson(GameStateFactory.toJson(dto.board));
        return repo.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    public static class SituationDTO {
        public String game;
        public int level;
        public Player player;
        public GameState board;
    }
}
