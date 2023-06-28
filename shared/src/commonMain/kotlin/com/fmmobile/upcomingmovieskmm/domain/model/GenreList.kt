package com.fmmobile.upcomingmovieskmm.domain.model

import com.fmmobile.upcomingmovieskmm.data.datasource.models.IGenreListModel
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IGenreModel
import kotlinx.serialization.Serializable

@Serializable
data class GenreList(
    override val genres: List<Genre>
): IGenreListModel

@Serializable
data class Genre (
    override val id: Long,
    override val name: String
): IGenreModel