package com.fmmobile.upcomingmovieskmm.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface IMovieListModel {
    val dates: IDatesModel
    val page: Long
    val results: List<IMovieModel>
    val totalPages: Long
    val totalResults: Long
}

interface IDatesModel {
    val maximum: String
    val minimum: String
}

interface IMovieModel {
    val adult: Boolean
    val backdropPath: String?
    val genreIDS: List<Long>
    val id: Long
    val originalLanguage: String
    val originalTitle: String
    val overview: String
    val popularity: Double
    val posterPath: String?
    val releaseDate: String
    val title: String
    val video: Boolean
    val voteAverage: Double
    val voteCount: Long
}

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
