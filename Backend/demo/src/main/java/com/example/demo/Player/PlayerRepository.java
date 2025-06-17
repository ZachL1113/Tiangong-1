package com.example.demo.Player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByName(String name);
}
