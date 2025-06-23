package com.example.demo.level;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByLevelIndex(int levelIndex);
    List<Level> findAllByOrderByLevelIndexAsc();
}
