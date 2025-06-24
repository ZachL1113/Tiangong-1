package com.example.demo.game.service.dto;

public record MoveRequest(String boardJson, String direction, Integer pieceId) {}
