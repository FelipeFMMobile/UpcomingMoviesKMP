package com.fmmobile.upcomingmovieskmp.domain.model

import com.fmmobile.upcomingmovieskmp.data.datasource.models.IGenreListModel
import com.fmmobile.upcomingmovieskmp.data.datasource.models.IGenreModel
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