package com.example.demo.level;

import jakarta.persistence.*;
import lombok.Data;
import com.example.demo.game.huarongdao.Board;

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
