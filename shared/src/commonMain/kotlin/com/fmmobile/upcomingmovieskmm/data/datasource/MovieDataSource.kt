package com.fmmobile.upcomingmovieskmm.data.datasource

import com.fmmobile.upcomingmovieskmm.data.datasource.models.IGenreListModel
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieListModel
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieModel

interface MovieDataSource {
    suspend fun getGenres(): Result<IGenreListModel>
    suspend fun getMovies(page: Int): Result<IMovieListModel>

    suspend fun saveMovie(movie: IMovieModel)

    suspend fun getMovie(movieId: Long) : IMovieModel?

    suspend fun removeMovie(movie: IMovieModel)
}