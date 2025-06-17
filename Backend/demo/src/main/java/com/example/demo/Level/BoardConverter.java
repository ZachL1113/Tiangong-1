package com.example.demo.Level;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.demo.Game.Huarongdao.src.huarongdao.Board;

@Converter
public class BoardConverter implements AttributeConverter<Board, String> {

    @Override
    public String convertToDatabaseColumn(Board attribute) {
        return attribute != null ? attribute.toJson() : null;
    }

    @Override
    public Board convertToEntityAttribute(String dbData) {
        return dbData != null ? Board.fromJson(dbData) : null;
    }
}