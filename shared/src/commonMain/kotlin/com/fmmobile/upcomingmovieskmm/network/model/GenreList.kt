package com.fmmobile.upcomingmovieskmm.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreList(
    val genres: List<Genre>
)

@Serializable
data class Genre (
    val id: Long,
    val name: String
)