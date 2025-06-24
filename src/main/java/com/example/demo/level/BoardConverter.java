package com.example.demo.level;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.demo.game.huarongdao.Board;
import com.example.demo.core.GameState;

@Converter
public class BoardConverter implements AttributeConverter<Board, String> {

    @Override
    public String convertToDatabaseColumn(Board attribute) {
        return attribute != null ? attribute.toJson() : null;
    }

    @Override
    public Board convertToEntityAttribute(String dbData) {
        return dbData != null ? GameState.fromJson(dbData, Board.class) : null;
    }
}
