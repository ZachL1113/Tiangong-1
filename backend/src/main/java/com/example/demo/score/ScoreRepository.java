package com.example.demo.score;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByGameOrderByScoreDesc(String game);
    List<Score> findByPlayerIdOrderByRecordedAtDesc(Long playerId);
    List<Score> findByPlayerIdAndGameOrderByScoreDesc(Long playerId, String game);

    @org.springframework.data.jpa.repository.Query("""
        SELECT s FROM Score s
        WHERE s.game = :game
          AND s.score = (
            SELECT MAX(s2.score) FROM Score s2
            WHERE s2.game = :game AND s2.player = s.player
          )
        ORDER BY s.score DESC
    """)
    List<Score> findBestScoresByGame(@org.springframework.data.repository.query.Param("game") String game);

    @org.springframework.data.jpa.repository.Query("""
        SELECT s FROM Score s
        WHERE s.game = :game AND s.level = :level
          AND s.score = (
            SELECT MAX(s2.score) FROM Score s2
            WHERE s2.game = :game AND s2.level = :level AND s2.player = s.player
          )
        ORDER BY s.score DESC
    """)
    List<Score> findBestScoresByGameAndLevel(@org.springframework.data.repository.query.Param("game") String game,
                                             @org.springframework.data.repository.query.Param("level") int level);

    boolean existsById(Long id);
}
