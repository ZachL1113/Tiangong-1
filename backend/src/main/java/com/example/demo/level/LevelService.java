package com.example.demo.level;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.game.huarongdao.Board;

@Service
public class LevelService {
    private final LevelRepository repo;

    public LevelService(LevelRepository repo) {
        this.repo = repo;
    }

    public List<Board> huarongdaoBoards() {
        return repo.findAllByOrderByLevelIndexAsc().stream()
                .map(Level::getBoard)
                .toList();
    }

    public Board huarongdaoBoard(int index) {
        Level l = repo.findByLevelIndex(index);
        return l != null ? l.getBoard() : null;
    }
}
