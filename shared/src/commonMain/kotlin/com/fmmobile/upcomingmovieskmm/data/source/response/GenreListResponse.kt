package com.fmmobile.upcomingmovieskmm.data.source.response

import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    val genres: List<GenreResponse>
)

@Serializable
data class GenreResponse(
    val id: Long,
    val name: String
)