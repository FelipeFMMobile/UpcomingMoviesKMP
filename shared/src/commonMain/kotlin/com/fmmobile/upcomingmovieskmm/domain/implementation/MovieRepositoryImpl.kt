package com.fmmobile.upcomingmovieskmm.domain.implementation

import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmm.data.source.response.MovieResponse
import com.fmmobile.upcomingmovieskmm.domain.model.Dates
import com.fmmobile.upcomingmovieskmm.domain.repository.MovieRepository
import com.fmmobile.upcomingmovieskmm.domain.model.Movie
import com.fmmobile.upcomingmovieskmm.domain.model.MovieList

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
): MovieRepository {
    override suspend fun getMovies(page: Int): MovieList {
        val result = remoteDataSource.getMovies(page)
        return MovieList(
            Dates(result.dates.maximum, result.dates.minimum),
            result.page,
            MovieWrapper.fromList(result.results),
            result.totalPages,
            result.totalResults
        )
    }
}

class MovieWrapper() {
    companion object {
        fun fromList(list: List<MovieResponse>) : List<Movie> {
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
                    it.posterPath,
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