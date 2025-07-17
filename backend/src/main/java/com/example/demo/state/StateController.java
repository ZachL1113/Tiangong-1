package com.example.demo.state;

import com.example.demo.player.Player;
import com.example.demo.core.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.core.GameStateFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/situations")
public class StateController {

    private final StateRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public StateController(StateRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public State add(@RequestBody SituationDTO dto) {
        State s = new State();
        s.setGame(dto.game());
        s.setLevel(dto.level());
        s.setPlayer(dto.player());
        try {
            s.setSituationJson(mapper.writeValueAsString(dto.board()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize board", e);
        }
        return repo.save(s);
    }

    @GetMapping
    public List<State> getAll() {
        return repo.findAll();
    }

    @GetMapping("/player/{playerId}/{game}")
    public List<State> byPlayer(@PathVariable Long playerId, @PathVariable String game) {
        return repo.findByPlayerIdAndGame(playerId, game);
    }

    @GetMapping("/{id}")
    public GameState getBoard(@PathVariable Long id) {
        State s = repo.findById(id).orElseThrow();
        return GameStateFactory.fromJson(s.getGame(), s.getSituationJson());
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @RequestBody SituationDTO dto) {
        State s = repo.findById(id).orElseThrow();
        s.setGame(dto.game());
        s.setLevel(dto.level());
        s.setPlayer(dto.player());
        try {
            s.setSituationJson(mapper.writeValueAsString(dto.board()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize board", e);
        }
        return repo.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    public record SituationDTO(String game, int level, Player player, Map<String, Object> board) {}
}
