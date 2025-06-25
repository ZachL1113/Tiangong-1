package com.example.demo.state;

import com.example.demo.player.Player;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class State {

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
