package com.cleaner.mediacleaner.controller

import com.cleaner.mediacleaner.domain.ScoredMovieEntry
import com.cleaner.mediacleaner.service.RecommendationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RecommendationController(private val recommendationService: RecommendationService) {

    @GetMapping("/recommendations")
    fun getRecommendations(): List<ScoredMovieEntry> {
        return recommendationService.getRecommendations()
    }
}