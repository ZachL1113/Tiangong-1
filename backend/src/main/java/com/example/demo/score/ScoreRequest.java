package com.example.demo.score.dto;

import java.time.LocalDateTime;

public record ScoreRequest(Long playerId, String game, int score, LocalDateTime recordedAt) {}
