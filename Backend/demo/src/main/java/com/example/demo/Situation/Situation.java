package com.example.demo.Situation;

import com.example.demo.Player.Player;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Situation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Player player;

    private String game;      

    private int level;

    @Lob
    private String situationJson; 
}
