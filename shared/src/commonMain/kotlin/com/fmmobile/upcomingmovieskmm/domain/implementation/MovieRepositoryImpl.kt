package com.fmmobile.upcomingmovieskmm.domain.implementation

import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieModel
import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRealmDataSource
import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmm.data.source.remote.Api
import com.fmmobile.upcomingmovieskmm.domain.model.Dates
import com.fmmobile.upcomingmovieskmm.domain.repository.MovieRepository
import com.fmmobile.upcomingmovieskmm.domain.model.Movie
import com.fmmobile.upcomingmovieskmm.domain.model.MovieList

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val realmDataSource: MovieRealmDataSource
): MovieRepository {
    override suspend fun getMovies(page: Int): MovieList {
        var movieList = MovieList(
            Dates("",""), 0, listOf(), 0, 0
        )
        remoteDataSource.getMovies(page).fold(
            onSuccess = { value ->
                movieList = MovieList(
                    Dates(value.dates.maximum, value.dates.minimum),
                    value.page,
                    MovieWrapper.fromList(value.results),
                    value.totalPages,
                    value.totalResults
                )
            },
            onFailure = {
            }
        )
        return movieList
    }

    override suspend fun getFavoriteMovies(page: Int): MovieList {
        var movieList = MovieList(
            Dates("",""), 0, listOf(), 0, 0
        )
        realmDataSource.getMovies(page).fold(
            onSuccess = { value ->
                movieList = MovieList(
                    Dates(value.dates.maximum, value.dates.minimum),
                    value.page,
                    MovieWrapper.fromList(value.results),
                    value.totalPages,
                    value.totalResults
                )
            },
            onFailure = {
            }
        )
        return movieList
    }

    override suspend fun saveMovie(movie: Movie) {
        // check if is already saved
        val movie = realmDataSource.getMovie(movie.id) ?:
        realmDataSource.saveMovie(movie)
    }

    override suspend fun removeMovie(movie: Movie) {
        realmDataSource.removeMovie(movie)
    }

    override suspend fun isSaved(movie: Movie): Boolean {
        return (realmDataSource.getMovie(movie.id) != null)
    }
}

class MovieWrapper() {
    companion object {
        fun fromList(list: List<IMovieModel>) : List<Movie> {
            return list.map {
                Movie(
                    it.adult,
                    it.backdropPath,
                    it.genreIDS,
                    it.id,
                    it.originalLanguage,
                    it.originalTitle,
                    it.overview,
                    it.popularity,
                    Api.imageDomain.domain + it.posterPath,
                    it.releaseDate,
                    it.title,
                    it.video,
                    it.voteAverage,
                    it.voteCount
                )
            }
        }
    }
}