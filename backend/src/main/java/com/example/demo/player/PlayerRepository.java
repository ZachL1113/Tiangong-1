package com.example.demo.player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByName(String name);
    Player findByProviderAndProviderId(String provider, String providerId);
}
