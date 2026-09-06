package com.cleaner.mediacleaner.scorer

import com.cleaner.mediacleaner.domain.MovieEntry
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class RecommendationScorerTest {

    @Test
    fun `scoreMovie combines all three signals correctly`() {
        val movie = MovieEntry(
            movieTitle = "Test Movie",
            dateAdded = LocalDateTime.now().minusYears(2),
            lastWatched = null,
            fileSizeBytes = 53687091200
        )

        val result = scoreMovie(movie)

        assertEquals(100.0, result.score)
    }

    @Test
    fun `unwatched movie receives unwatched bonus`() {
        val result = scoreMovieWatchedStatus(lastWatchedDate = null, unwatchedMaxScore = 20.0)
        assertEquals(20.0, result)
    }

    @Test
    fun `watched movie receives no unwatched bonus`() {
        val result = scoreMovieWatchedStatus(lastWatchedDate = LocalDateTime.now(), unwatchedMaxScore = 20.0)
        assertEquals(0.0, result)
    }

    @Test
    fun `movie older than cutoff receives max recency score`() {
        val result =
            scoreMovieDateAdded(LocalDateTime.now().minusYears(10), recentlyAddedCutoffMonths = 3, recencyWeight = 60.0)
        assertEquals(60.0, result)
    }

    @Test
    fun `movie added recently receives zero recency score`() {
        val result =
            scoreMovieDateAdded(LocalDateTime.now().minusMonths(1), recentlyAddedCutoffMonths = 3, recencyWeight = 60.0)
        assertEquals(0.0, result)
    }

    @Test
    fun `large file receives max size score`() {
        val result = scoreMovieFileSize(
            movieFileSizeBytes = 53687091200,
            fileSizeMinLimitGB = 5.0,
            fileSizeMaxLimitGB = 20.0,
            fileSizeMaxScore = 20.0
        )
        assertEquals(20.0, result)
    }

    @Test
    fun `small file receives zero size score`() {
        val result = scoreMovieFileSize(
            movieFileSizeBytes = 1024,
            fileSizeMinLimitGB = 5.0,
            fileSizeMaxLimitGB = 20.0,
            fileSizeMaxScore = 20.0
        )
        assertEquals(0.0, result)
    }

    @Test
    fun `rankMovies returns highest scoring movie first`() {
        val lowScoringMovie = MovieEntry(
            movieTitle = "Low Scoring Movie",
            dateAdded = LocalDateTime.now().minusDays(7),
            lastWatched = LocalDateTime.now(),
            fileSizeBytes = 1024,
        )
        val highScoringMovie = MovieEntry(
            movieTitle = "High Scoring Movie",
            dateAdded = LocalDateTime.now().minusYears(3),
            lastWatched = null,
            fileSizeBytes = 53687091200,
        )
        val movielist = listOf(lowScoringMovie, highScoringMovie)
        val result = rankMovies(movielist)

        assertEquals(highScoringMovie, result.first().movie)
        assertEquals(lowScoringMovie, result.last().movie)
    }
}