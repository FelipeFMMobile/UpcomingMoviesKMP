package com.fmmobile.upcomingmovieskmp.domain.model

import com.fmmobile.upcomingmovieskmp.data.datasource.models.IDatesModel
import com.fmmobile.upcomingmovieskmp.data.datasource.models.IMovieListModel
import com.fmmobile.upcomingmovieskmp.data.datasource.models.IMovieModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieList (
    override val dates: Dates,
    override val page: Long,
    override val results: List<Movie>,

    @SerialName("total_pages")
    override val totalPages: Long,

    @SerialName("total_results")
    override val totalResults: Long
): IMovieListModel

@Serializable
data class Dates (
    override val maximum: String,
    override val minimum: String
): IDatesModel

@Serializable
data class Movie (
    override val adult: Boolean,

    @SerialName("backdrop_path")
    override val backdropPath: String?,

    @SerialName( "genre_ids")
    override val genreIDS: List<Long>,

    override val id: Long,

    @SerialName("original_language")
    override val originalLanguage: String,

    @SerialName("original_title")
    override val originalTitle: String,

    override val overview: String,
    override val popularity: Double,

    @SerialName("poster_path")
    override val posterPath: String?,

    @SerialName("release_date")
    override val releaseDate: String,

    override val title: String,
    override val video: Boolean,

    @SerialName("vote_average")
    override val voteAverage: Double,

    @SerialName("vote_count")
    override val voteCount: Long
): IMovieModel

