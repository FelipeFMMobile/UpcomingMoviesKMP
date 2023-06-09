package com.fmmobile.upcomingmovieskmm.domain.usecase

import com.fmmobile.upcomingmovieskmm.data.source.remote.NetworkServices
import com.fmmobile.upcomingmovieskmm.domain.model.GenreList
import com.fmmobile.upcomingmovieskmm.domain.model.MovieList
import com.fmmobile.upcomingmovieskmm.domain.repository.GenreRepository
import com.fmmobile.upcomingmovieskmm.domain.repository.MovieRepository
import io.ktor.serialization.JsonConvertException

class GetMovieListUseCase(
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository
) {
    var genres: GenreList? = null
    var movies: MovieList? = null
    var currentPage = 0
    var service= NetworkServices()
    @Throws(Exception::class)
    suspend fun loadMovies(page: Int): MovieList {
        try {
            this.genres = genreRepository.getGenres()
            val movies = movieRepository.getMovies(page)
            currentPage = page
            this.movies = movies
            return movies
        } catch (cause: Throwable) {
            throw JsonConvertException("Illegal input:" + cause.printStackTrace(), cause)
        }
    }
}