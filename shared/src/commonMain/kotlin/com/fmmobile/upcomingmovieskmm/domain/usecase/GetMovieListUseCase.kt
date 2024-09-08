package com.fmmobile.upcomingmovieskmm.domain.usecase


import com.fmmobile.upcomingmovieskmm.domain.model.GenreList
import com.fmmobile.upcomingmovieskmm.domain.model.Movie
import com.fmmobile.upcomingmovieskmm.domain.model.MovieList
import com.fmmobile.upcomingmovieskmm.domain.repository.GenreRepository
import com.fmmobile.upcomingmovieskmm.domain.repository.LocationInfo
import com.fmmobile.upcomingmovieskmm.domain.repository.LocationRepository
import com.fmmobile.upcomingmovieskmm.domain.repository.MovieRepository

class GetMovieListUseCase(
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository
) {
    var genres: GenreList? = null
    var movies: MovieList? = null
    var currentPage = 0
    var selectedMovie: Movie? = null

    @Throws(Exception::class)
    suspend fun loadMovies(page: Int): MovieList {
        this.genres = genreRepository.getGenres()
        var movies = movieRepository.getMovies(page)
        if (movies.results.isEmpty()) {
            movies = movieRepository.getFavoriteMovies(page)
        }
        currentPage = page
        this.movies = movies
        return movies
    }

    suspend fun saveMovie(movie: Movie) {
        movieRepository.saveMovie(movie)
    }

    suspend fun removeMovie(movie: Movie) {
        return movieRepository.removeMovie(movie)
    }
    suspend fun isSaved(movie: Movie) : Boolean {
        return movieRepository.isSaved(movie)
    }

    fun selectMovie(movie: Movie) {
        selectedMovie = movie
    }

    suspend fun getLocation(): LocationInfo {
        return LocationRepository.make()
    }

}