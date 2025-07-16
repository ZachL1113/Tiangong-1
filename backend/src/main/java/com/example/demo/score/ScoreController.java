package com.example.demo.score;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class ScoreController {

    private final ScoreRepository repo;

    public ScoreController(ScoreRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/leaderboard")
    public List<Score> leaderboard(@RequestParam String game) {
        return repo.findByGameOrderByScoreDesc(game);
    }

    @GetMapping("/players/{id}/scores")
    public List<Score> playerScores(@PathVariable Long id) {
        return repo.findByPlayerIdOrderByRecordedAtDesc(id);
    }
    @GetMapping("/players/{id}/leaderboard-rank")
    public int playerRank(@PathVariable Long id, @RequestParam String game) {
        List<Score> scores = repo.findByGameOrderByScoreDesc(game);
        int rank = 1;
        for (Score s : scores) {
            if (s.getPlayer() != null && id.equals(s.getPlayer().getId())) {
                return rank;
            }
            rank++;
        }
        return -1;
    }

    @GetMapping("/history/{id}")
    public Score history(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping("/scores")
    public Score addScore(@RequestBody Score score) {
        score.setId(null);
        if (score.getRecordedAt() == null) {
            score.setRecordedAt(java.time.LocalDateTime.now());
        }
        return repo.save(score);
    }

    @PutMapping("/scores/{id}")
    public Score updateScore(@PathVariable Long id, @RequestBody Score updated) {
        Score existing = repo.findById(id).orElseThrow();
        existing.setScore(updated.getScore());
        existing.setGame(updated.getGame());
        existing.setPlayer(updated.getPlayer());
        if (updated.getRecordedAt() != null) {
            existing.setRecordedAt(updated.getRecordedAt());
        }
        return repo.save(existing);
    }

    @DeleteMapping("/scores/{id}")
    public void deleteScore(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
