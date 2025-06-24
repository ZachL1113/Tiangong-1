package com.example.demo.player;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerRepository repo;

    public PlayerController(PlayerRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Player addPlayer(@RequestBody Player player) {
        return repo.save(player);
    }
    @GetMapping
    public List<Player> getAllPlayers(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Long id){
        return repo.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id,@RequestBody Player updated){
        Player existing = repo.findById(id).orElseThrow();
        existing.setName(updated.getName());
        existing.setScore(updated.getScore());
        existing.setLevel(updated.getLevel());
        existing.setGamesPlayed(updated.getGamesPlayed());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        repo.deleteById(id);
    }

}
