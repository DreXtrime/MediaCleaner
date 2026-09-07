---

# MediaCleaner

A REST API for identifying movies that are good candidates for deletion from a Plex media server, based on watch history and file size.

Built as a focused Kotlin + Spring Boot project.

---

## What it does

MediaCleaner connects to a self-hosted Tautulli instance and returns a ranked list of movies weighted by:

- Time since the movie was added
- File size
- Whether the movie has never been watched

The result is a single endpoint that tells you what to delete first to recover the most disk space.

---

## Tech Stack

- Kotlin with Spring Boot 4
- Maven

---

## Getting Started

### Prerequisites

- Java 21
- A running Tautulli instance

### Configuration

Copy the example environment file and fill in your values:

```bash
cp .env.example .env
```

```
API_KEY=your-long-random-api-key

TAUTULLI_BASE_URL=http://your-tautulli-url
TAUTULLI_API_KEY=your-api-key
```

### Running

```bash
mvn spring-boot:run
```
### Docker

Included is a docker compose script that uses a automatically prebuilt ghcr image
```bash
docker compose up -d
```

### Testing

```bash
mvn test
```

---

## API

All requests require an **Api-Key** to be present in the request. Set the api key using the `.env`

### GET /recommendations

Returns a ranked list of movies recommended for deletion, highest priority first.

```json
[
  {
    "movie": {
      "movieTitle": "Example Movie",
      "dateAdded": "2021-03-01T10:00:00",
      "lastWatched": null,
      "fileSizeBytes": 8531658825
    },
    "score": 83.9
  }
]
```

---

## Current Limitations

This is an MVP. Known limitations:

- Movies only, no TV show support
- Single Plex library section
- No per-user watch history, aggregate play data only
- No delete functionality, read-only recommendations
- Hardcoded values for scoring

---

## Future Plans

- Per-user watch history via Tautulli
- TV show and season support
- Configurable scoring weights via request parameters or user preferences
- Delete and unmonitor integration with Radarr/Sonarr to act on recommendations directly
- Caching layer to avoid repeated API calls on every request
- Connection to Radarr and Sonarr for easy deletion of media
- Frontend

---