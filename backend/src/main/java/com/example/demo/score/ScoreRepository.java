package com.example.demo.score;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByGameOrderByScoreDesc(String game);
    List<Score> findByPlayerIdOrderByRecordedAtDesc(Long playerId);
    List<Score> findByPlayerIdAndGameOrderByScoreDesc(Long playerId, String game);

    boolean existsById(Long id);
}
