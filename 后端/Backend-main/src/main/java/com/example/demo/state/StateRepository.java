package com.example.demo.state;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.player.Player;

public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findByPlayerAndGame(Player player, String game);
    List<State> findByPlayerIdAndGame(Long playerId, String game);
}
