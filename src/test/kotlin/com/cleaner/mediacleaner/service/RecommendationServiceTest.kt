package com.cleaner.mediacleaner.service

import com.cleaner.mediacleaner.client.tautulli.TautulliClient
import com.cleaner.mediacleaner.client.tautulli.TautulliMovieEntry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import kotlin.test.assertEquals


class RecommendationServiceTest {
    private val tautulliClient = mock<TautulliClient>()
    private val service = RecommendationService(tautulliClient)

    @Test
    fun `unix timestamp converts correctly to LocalDateTime`() {
        val tautulliMovieEntry = TautulliMovieEntry(
            ratingKey = "Test key",
            title = "Test title",
            addedAt = 1600000000,
            lastPlayed = null,
            fileSize = 1600000000,
            playCount = 1,
        )
        val result = service.mapToMovieEntry(tautulliMovieEntry)
        assertEquals(LocalDateTime.parse("2020-09-13T12:26:40"), result.dateAdded)
    }

    @Test
    fun `null last played maps to null`() {
        val tautulliMovieEntry = TautulliMovieEntry(
            ratingKey = "Test key",
            title = "Test title",
            addedAt = 1600000000,
            lastPlayed = null,
            fileSize = 1600000000,
            playCount = 1,
        )
        val result = service.mapToMovieEntry(tautulliMovieEntry)
        assertNull(result.lastWatched)
    }

    @Test
    fun `filesize 0 maps correctly`() {
        val tautulliMovieEntry = TautulliMovieEntry(
            ratingKey = "Test key",
            title = "Test title",
            addedAt = 1600000000,
            lastPlayed = null,
            fileSize = 0,
            playCount = 1,
        )
        val result = service.mapToMovieEntry(tautulliMovieEntry)
        assertEquals(0L, result.fileSizeBytes)
    }
}