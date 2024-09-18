package com.fmmobile.upcomingmovieskmp.domain.repository

import com.fmmobile.upcomingmovieskmp.domain.model.Movie
import com.fmmobile.upcomingmovieskmp.domain.model.MovieList

interface MovieRepository {
    suspend fun getMovies(page: Int): MovieList

    suspend fun getFavoriteMovies(page: Int): MovieList

    suspend fun saveMovie(movie: Movie)

    suspend fun removeMovie(movie: Movie)

    suspend fun isSaved(movie: Movie): Boolean
}