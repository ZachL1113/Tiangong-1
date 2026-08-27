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

- Third-party OAuth login requires the configuration described below.
- Leaderboard has known issues under some conditions.
- Not yet deployed — runs locally.

## Running locally

The Maven build packages the frontend into the Spring Boot application so OAuth and API requests use the same origin.

```bash
cd backend
export GOOGLE_OAUTH_CLIENT_ID="your-client-id"
export GOOGLE_OAUTH_CLIENT_SECRET="your-client-secret"
bash ./mvnw spring-boot:run
```

Then open `http://localhost:8080/login.html`. Do not open the frontend HTML files directly from the filesystem.

### Google OAuth configuration

Create or rotate a Google OAuth 2.0 client in Google Cloud Console. Never commit its credentials to this repository.

Set the credentials as environment variables before starting the backend:

```bash
export GOOGLE_OAUTH_CLIENT_ID="your-client-id"
export GOOGLE_OAUTH_CLIENT_SECRET="your-client-secret"
cd backend
./mvnw spring-boot:run
```

For local development, add this authorized redirect URI to the Google OAuth client:

```text
http://localhost:8080/login/oauth2/code/google
```

For deployment, add the equivalent HTTPS callback on the backend's public domain:

```text
https://YOUR_BACKEND_DOMAIN/login/oauth2/code/google
```

Configure the same two environment variables in the deployment platform's secret manager. Do not place production credentials in `.env`, `application.yaml`, frontend code, screenshots, or logs.
