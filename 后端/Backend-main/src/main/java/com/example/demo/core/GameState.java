package com.example.demo.core;

import com.fasterxml.jackson.databind.ObjectMapper;
public interface GameState {

    ObjectMapper MAPPER = new ObjectMapper();

    default String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialise game state", e);
        }
    }

    static <T extends GameState> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize game state", e);
        }
    }
}
