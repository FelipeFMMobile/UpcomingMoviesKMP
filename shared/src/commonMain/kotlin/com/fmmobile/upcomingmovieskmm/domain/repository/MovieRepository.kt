package com.fmmobile.upcomingmovieskmm.domain.repository

import com.fmmobile.upcomingmovieskmm.domain.model.MovieList

interface MovieRepository {
    suspend fun getMovies(page: Int): MovieList
}