package com.fmmobile.upcomingmovieskmm.domain.implementation

import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmm.data.source.response.GenreListResponse
import com.fmmobile.upcomingmovieskmm.data.source.response.GenreResponse
import com.fmmobile.upcomingmovieskmm.domain.model.Genre
import com.fmmobile.upcomingmovieskmm.domain.model.GenreList
import com.fmmobile.upcomingmovieskmm.domain.repository.GenreRepository

class GenreRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
): GenreRepository {
    override suspend fun getGenres(): GenreList {
        val result: Result<GenreListResponse> = remoteDataSource.getGenres()
        var genreList = GenreList(genres = listOf())
        result.fold(
            onSuccess = { value ->
                genreList = GenreList(
                    genres = GenreWrapper.fromList(value.genres)
                )
            },
            onFailure = {}
        )
        return genreList
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