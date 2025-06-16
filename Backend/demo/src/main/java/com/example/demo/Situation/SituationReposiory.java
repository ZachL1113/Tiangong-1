package com.example.demo.Situation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Player.Player;

public interface SituationReposiory extends JpaRepository<Situation,Long>{
     List<Situation> findByPlayerAndGame(Player player, String game);
}
