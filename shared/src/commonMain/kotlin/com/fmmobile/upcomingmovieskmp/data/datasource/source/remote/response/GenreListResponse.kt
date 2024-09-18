package com.fmmobile.upcomingmovieskmp.data.datasource.source.remote.response

import com.fmmobile.upcomingmovieskmp.data.datasource.models.IGenreListModel
import com.fmmobile.upcomingmovieskmp.data.datasource.models.IGenreModel
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    override val genres: List<GenreResponse>
): IGenreListModel

@Serializable
data class GenreResponse(
    override val id: Long,
    override val name: String
): IGenreModel