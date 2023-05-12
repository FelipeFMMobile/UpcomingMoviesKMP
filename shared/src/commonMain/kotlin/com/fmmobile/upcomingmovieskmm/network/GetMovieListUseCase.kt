package com.fmmobile.upcomingmovieskmm.network

import com.fmmobile.upcomingmovieskmm.network.model.GenreList
import com.fmmobile.upcomingmovieskmm.network.model.MovieList
import io.ktor.serialization.JsonConvertException

// TODO: This will be parsed to clean architeture (data-domain) futter
class GetMovieListUseCase {
    var genres: GenreList? = null
    var movies: MovieList? = null
    var currentPage = 0
    var service= Service()
    @Throws(Exception::class)
    suspend fun loadMovies(page: Int): MovieList {
        try {
            this.genres = service.getGenres()
            val movies = service.getMovies(page)
            currentPage = page
            this.movies = movies
            return movies
        } catch (cause: Throwable) {
            throw JsonConvertException("Illegal input:" + cause.printStackTrace(), cause)
        }
    }
}