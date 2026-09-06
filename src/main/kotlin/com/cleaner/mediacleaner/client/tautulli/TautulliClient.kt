package com.cleaner.mediacleaner.client.tautulli

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body


@Component
class TautulliClient(
    @Value("\${tautulli.base-url}") private val baseUrl: String,
    @Value("\${tautulli.api-key}") private val apiKey: String,
) {
    private val restClient = RestClient.create()

    fun getMovieLibraryMedia(): List<TautulliMovieEntry> {
        val response = restClient.get()
            // assume section 1
            .uri("$baseUrl/api/v2?apikey=$apiKey&cmd=get_library_media_info&section_id=1&media_type=movie&length=1000")
            .retrieve()
            .body<TautulliLibraryResponse>()
        return response?.response?.data?.data ?: emptyList()
    }
}