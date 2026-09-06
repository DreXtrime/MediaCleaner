package com.cleaner.mediacleaner.domain

data class ScoredMovieEntry(
    val movie: MovieEntry,
    val score: Double,
)