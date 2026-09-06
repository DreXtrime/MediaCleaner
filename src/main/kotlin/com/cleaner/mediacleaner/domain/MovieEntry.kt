package com.cleaner.mediacleaner.domain

import java.time.LocalDateTime

data class MovieEntry(
    val movieTitle: String,
    val dateAdded: LocalDateTime,
    val lastWatched: LocalDateTime?,
    val fileSizeBytes: Long,
)