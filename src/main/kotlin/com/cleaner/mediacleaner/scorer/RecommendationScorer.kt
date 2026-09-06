package com.cleaner.mediacleaner.scorer

import com.cleaner.mediacleaner.domain.MovieEntry
import com.cleaner.mediacleaner.domain.ScoredMovieEntry
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun rankMovies(movies: List<MovieEntry>): List<ScoredMovieEntry> =
    movies.map { scoreMovie(it) }.sortedByDescending { it.score }

fun scoreMovie(
    movie: MovieEntry,
    unwatchedMaxScore: Double = 20.0,
    recencyWeight: Double = 60.0,
    cutoffMonths: Long = 3,
    fileSizeMaxScore: Double = 20.0,
    fileSizeMinLimitGB: Double = 5.0,
    fileSizeMaxLimitGB: Double = 20.0
): ScoredMovieEntry {
    val recencyScore = scoreMovieDateAdded(movie.dateAdded, cutoffMonths, recencyWeight)
    val fileSizeScore =
        scoreMovieFileSize(movie.fileSizeBytes, fileSizeMinLimitGB, fileSizeMaxLimitGB, fileSizeMaxScore)
    val unwatchedScore = scoreMovieWatchedStatus(movie.lastWatched, unwatchedMaxScore)
    return ScoredMovieEntry(movie, unwatchedScore + fileSizeScore + recencyScore)
}

internal fun scoreMovieFileSize(
    movieFileSizeBytes: Long, fileSizeMinLimitGB: Double, fileSizeMaxLimitGB: Double, fileSizeMaxScore: Double
): Double {
    val fileSizeGB = movieFileSizeBytes / (1024.0 * 1024.0 * 1024.0)
    return ((fileSizeGB - fileSizeMinLimitGB) / (fileSizeMaxLimitGB - fileSizeMinLimitGB))
        .coerceIn(0.0, 1.0) * fileSizeMaxScore
}

internal fun scoreMovieWatchedStatus(lastWatchedDate: LocalDateTime?, unwatchedMaxScore: Double): Double =
    if (lastWatchedDate == null) unwatchedMaxScore else 0.0

internal fun scoreMovieDateAdded(
    movieDateAdded: LocalDateTime, recentlyAddedCutoffMonths: Long, recencyWeight: Double
): Double {
    val currentTime = LocalDateTime.now()
    val monthsOld = ChronoUnit.MONTHS.between(movieDateAdded, currentTime)
    return ((monthsOld - recentlyAddedCutoffMonths) / 8.0).coerceIn(0.0, 1.0) * recencyWeight
}