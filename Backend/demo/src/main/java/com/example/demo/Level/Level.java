package com.example.demo.Level;

import jakarta.persistence.*;
import lombok.Data;
import com.example.demo.Game.Huarongdao.src.huarongdao.Board;

@Data
@Entity
public class Level {
    @Id
    @GeneratedValue
    private Long id;

    private int levelIndex;

    @Lob
    @Convert(converter = BoardConverter.class)
    private Board board;

    public Level() {}
}