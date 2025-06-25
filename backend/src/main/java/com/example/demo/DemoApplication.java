package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;

import com.example.demo.core.GameStateFactory;
import com.example.demo.game.g2048.Board;

@SpringBootApplication
public class DemoApplication {
        public static void main(String[] args) {
                SpringApplication.run(DemoApplication.class, args);
        }

        @PostConstruct
        public void registerGameStates() {
                GameStateFactory.register("2048", Board.class);
                GameStateFactory.register("huarongdao",
                        com.example.demo.game.huarongdao.Board.class);
        }
}
