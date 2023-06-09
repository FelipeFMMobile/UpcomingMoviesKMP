package com.fmmobile.upcomingmovieskmm.domain.model

import kotlinx.serialization.Serializable

interface IGenreListModel {
    val genres: List<IGenreModel>
}

interface IGenreModel {
    val id: Long
    val name: String
}

@Serializable
data class GenreList(
    override val genres: List<Genre>
): IGenreListModel

@Serializable
data class Genre (
    override val id: Long,
    override val name: String
): IGenreModel