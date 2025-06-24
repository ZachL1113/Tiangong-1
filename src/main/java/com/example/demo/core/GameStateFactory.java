package com.example.demo.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GameStateFactory {
    private static final ObjectMapper mapper = GameState.MAPPER;

    private static final Map<String, Function<String, GameState>> deserializerMap = new HashMap<>();

    public static void register(String gameName, Class<? extends GameState> clazz) {
        deserializerMap.put(gameName, json -> {
            try {
                return mapper.readValue(json, clazz);
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize " + gameName, e);
            }
        });
    }

    public static GameState fromJson(String gameName, String json) {
        Function<String, GameState> deserializer = deserializerMap.get(gameName);
        if (deserializer == null) {
            throw new IllegalArgumentException("Unsupported game type: " + gameName);
        }
        return deserializer.apply(json);
    }

    public static String toJson(GameState board) {
        try {
            return mapper.writeValueAsString(board);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize board", e);
        }
    }
}
