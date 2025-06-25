package com.example.demo.level;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.game.huarongdao.Board;

@Component
public class DataLoader implements CommandLineRunner {
    private final LevelRepository repo;

    public DataLoader(LevelRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            List<Board> defaults = HuarongdaoDefaults.boards();
            for (int i = 0; i < defaults.size(); i++) {
                Level l = new Level();
                l.setLevelIndex(i);
                l.setBoard(defaults.get(i).copy());
                repo.save(l);
            }
        }
    }
}
