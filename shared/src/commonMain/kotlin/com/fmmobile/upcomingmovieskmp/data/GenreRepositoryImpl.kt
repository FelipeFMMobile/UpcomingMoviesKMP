package com.fmmobile.upcomingmovieskmp.data

import com.fmmobile.upcomingmovieskmp.data.datasource.source.remote.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmp.data.datasource.source.remote.response.GenreListResponse
import com.fmmobile.upcomingmovieskmp.data.datasource.source.remote.response.GenreResponse
import com.fmmobile.upcomingmovieskmp.domain.model.Genre
import com.fmmobile.upcomingmovieskmp.domain.model.GenreList
import com.fmmobile.upcomingmovieskmp.domain.repository.GenreRepository
import io.github.aakira.napier.Napier

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
            onFailure = {
                Napier.e("genre load failure", it)
            }
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