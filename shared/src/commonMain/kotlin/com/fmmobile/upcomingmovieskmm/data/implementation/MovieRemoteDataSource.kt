package com.fmmobile.upcomingmovieskmm.data.implementation

import com.fmmobile.upcomingmovieskmm.data.datasource.MovieDataSource
import com.fmmobile.upcomingmovieskmm.data.source.response.GenreListResponse
import com.fmmobile.upcomingmovieskmm.data.source.response.MovieListResponse
import com.fmmobile.upcomingmovieskmm.data.source.remote.EndPoint
import com.fmmobile.upcomingmovieskmm.data.source.remote.NetworkServices

class MovieRemoteDataSource(
    private val service: NetworkServices = NetworkServices()
): MovieDataSource {
    override suspend fun getGenres(): GenreListResponse {
        return service.run(EndPoint.genre)
    }

    override suspend fun getMovies(page: Int): MovieListResponse {
        val params = mapOf("page" to page.toString())
        return service.run(EndPoint.upcoming, params)
    }
}