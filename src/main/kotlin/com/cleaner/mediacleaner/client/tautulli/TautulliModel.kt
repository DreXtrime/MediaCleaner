package com.cleaner.mediacleaner.client.tautulli

import com.fasterxml.jackson.annotation.JsonProperty

// data class TautulliHistoryResponse(
//    val response: TautulliResponseWrapper
//)
//
// data class TautulliResponseWrapper(
//    val result: String,
//    val data: TautulliHistoryData
//)
//
// data class TautulliHistoryData(
//    val data: List<TautulliHistoryEntry>
//)
//
// data class TautulliHistoryEntry(
//    @JsonProperty("full_title") val fullTitle: String,
//    @JsonProperty("rating_key") val ratingKey: Int,
//    @JsonProperty("date") val lastWatched: Long,
//    @JsonProperty("watched_status") val watchedStatus: Int,
//    @JsonProperty("percent_complete") val percentComplete: Int
//)

data class TautulliLibraryResponse(
    val response: TautulliLibraryWrapper
)

data class TautulliLibraryWrapper(
    val result: String,
    val data: TautulliLibraryData
)

data class TautulliLibraryData(
    val data: List<TautulliMovieEntry>
)

data class TautulliMovieEntry(
    @JsonProperty("rating_key") val ratingKey: String,
    val title: String,
    @JsonProperty("added_at") val addedAt: Long,
    @JsonProperty("last_played") val lastPlayed: Long?,
    @JsonProperty("file_size") val fileSize: Long,
    @JsonProperty("play_count") val playCount: Int?

)