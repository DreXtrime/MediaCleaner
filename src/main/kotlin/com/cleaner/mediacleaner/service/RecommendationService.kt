package com.cleaner.mediacleaner.service

import com.cleaner.mediacleaner.client.tautulli.TautulliClient
import com.cleaner.mediacleaner.client.tautulli.TautulliMovieEntry
import com.cleaner.mediacleaner.domain.MovieEntry
import com.cleaner.mediacleaner.domain.ScoredMovieEntry
import com.cleaner.mediacleaner.scorer.rankMovies
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class RecommendationService(
    private val tautulliClient: TautulliClient,
) {

    fun getRecommendations(): List<ScoredMovieEntry> {
        val tautulliLibraryMedia = tautulliClient.getMovieLibraryMedia()
        val movies = tautulliLibraryMedia.map { mapToMovieEntry(it) }
        return rankMovies(movies)
    }

    internal fun mapToMovieEntry(entry: TautulliMovieEntry): MovieEntry {
        return MovieEntry(
            movieTitle = entry.title,
            dateAdded = LocalDateTime.ofEpochSecond(entry.addedAt, 0, ZoneOffset.UTC),
            lastWatched = entry.lastPlayed?.let { LocalDateTime.ofEpochSecond(entry.addedAt, 0, ZoneOffset.UTC) },
            fileSizeBytes = entry.fileSize,
        )
    }
}