package com.fmmobile.upcomingmovieskmm.data.datasource

import com.fmmobile.upcomingmovieskmm.data.source.response.GenreListResponse
import com.fmmobile.upcomingmovieskmm.data.source.response.MovieListResponse

interface MovieDataSource {
    suspend fun getGenres(): GenreListResponse
    suspend fun getMovies(page: Int): MovieListResponse
}