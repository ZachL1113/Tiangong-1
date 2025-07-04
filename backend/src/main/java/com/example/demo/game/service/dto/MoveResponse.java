package com.example.demo.game.service.dto;

public record MoveResponse(String boardJson, boolean valid, boolean success, Integer gameEnd) {}
