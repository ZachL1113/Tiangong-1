# Tiangong-1

A web game platform with user accounts and leaderboards. Built for NUS Orbital 2025 (Gemini level) by a two-person team.

Two complete games — Klotski (sliding block puzzle) and Snake — share a common login and scoring layer. Klotski ranks players by move count, Snake by score.

## Stack

- Frontend: JavaScript
- Backend: Java / Spring Boot
- Database: SQL

## Features

- User registration and login
- Per-game persistent leaderboards
- Two fully playable games

## Known limitations

- Third-party OAuth login is incomplete; built-in accounts work.
- Leaderboard has known issues under some conditions.
- Not yet deployed — runs locally.

## Running locally

Open `index.html` in a browser to play both games.

Account login and leaderboards require the Spring Boot backend and a local SQL database to be running.
