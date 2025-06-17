package com.example.demo.State;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Player.Player;

public interface StateRepository extends JpaRepository<State,Long>{
     List<State> findByPlayerAndGame(Player player, String game);
}
