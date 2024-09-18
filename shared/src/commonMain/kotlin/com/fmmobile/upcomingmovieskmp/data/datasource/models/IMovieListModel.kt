package com.fmmobile.upcomingmovieskmp.data.datasource.models

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