package com.example.demo.player;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Player{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int level;
    private int score;
    private int gamesPlayed;

    public Player() {}

}

