package com.fmmobile.upcomingmovieskmm.domain.implementation

import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmm.data.source.response.GenreResponse
import com.fmmobile.upcomingmovieskmm.data.source.response.MovieResponse
import com.fmmobile.upcomingmovieskmm.domain.model.Dates
import com.fmmobile.upcomingmovieskmm.domain.model.Genre
import com.fmmobile.upcomingmovieskmm.domain.model.GenreList
import com.fmmobile.upcomingmovieskmm.domain.repository.MovieRepository
import com.fmmobile.upcomingmovieskmm.domain.model.Movie
import com.fmmobile.upcomingmovieskmm.domain.model.MovieList
import com.fmmobile.upcomingmovieskmm.domain.repository.GenreRepository

class GenreRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
): GenreRepository {
    override suspend fun getGenres(): GenreList {
        val result = remoteDataSource.getGenres()
        return GenreList(
            genres = GenreWrapper.fromList(result.genres)
        )
    }
}

class GenreWrapper() {
    companion object {
        fun fromList(list: List<GenreResponse>) : List<Genre> {
            return list.map {
                Genre(
                    it.id,
                    it.name
                )
            }
        }
    }
}