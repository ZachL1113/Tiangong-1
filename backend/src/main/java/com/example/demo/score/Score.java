package com.example.demo.score;

import com.example.demo.player.Player;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Score {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Player player;

    private String game;
    private Integer level;
    private int score;
    private LocalDateTime recordedAt;

    public Score() {
        this.recordedAt = LocalDateTime.now();
        this.level = 0;
    }
}
