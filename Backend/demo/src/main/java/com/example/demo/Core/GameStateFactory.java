package com.example.demo.Core;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GameStateFactory {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(GameState board) {
        try {
            return mapper.writeValueAsString(board);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize board", e);
        }
    }

    public static GameState fromJson(String gameName, String json) {
        try {
            if (gameName.equals("2048")) {
                return mapper.readValue(json, com.example.demo.Game.Game2048.src.Board.class);
            } else if (gameName.equals("huarongdao")) {
                return mapper.readValue(json, com.example.demo.Game.Huarongdao.src.huarongdao.Board.class);
            }
            throw new IllegalArgumentException("Unknown game type");
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize board", e);
        }
    }
}
