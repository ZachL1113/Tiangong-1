package com.example.demo.level;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelController {

    private final LevelRepository repo;

    public LevelController(LevelRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Level add(@RequestBody Level level) {
        return repo.save(level);
    }

    @GetMapping
    public List<Level> all() {
        return repo.findAllByOrderByLevelIndexAsc();
    }

    @GetMapping("/{id}")
    public Level get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Level update(@PathVariable Long id, @RequestBody Level update) {
        Level existing = repo.findById(id).orElseThrow();
        existing.setLevelIndex(update.getLevelIndex());
        existing.setBoard(update.getBoard());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
