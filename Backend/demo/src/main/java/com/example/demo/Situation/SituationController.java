package com.example.demo.Situation;

import com.example.demo.Player.Player;
import com.example.demo.Core.GameState;
import com.example.demo.Core.GameStateFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/situations")
public class SituationController {

    private final SituationReposiory repo;

    public SituationController(SituationReposiory repo) {
        this.repo = repo;
    }

    @PostMapping
    public Situation add(@RequestBody SituationDTO dto) {
        Situation s = new Situation();
        s.setGame(dto.game);
        s.setLevel(dto.level);
        s.setPlayer(dto.player);
        s.setSituationJson(GameStateFactory.toJson(dto.board));
        return repo.save(s);
    }

    @GetMapping
    public List<Situation> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public GameState getBoard(@PathVariable Long id) {
        Situation s = repo.findById(id).orElseThrow();
        return GameStateFactory.fromJson(s.getGame(), s.getSituationJson());
    }

    @PutMapping("/{id}")
    public Situation update(@PathVariable Long id, @RequestBody SituationDTO dto) {
        Situation s = repo.findById(id).orElseThrow();
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
