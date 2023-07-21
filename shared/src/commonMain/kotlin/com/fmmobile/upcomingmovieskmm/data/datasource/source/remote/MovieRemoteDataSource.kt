package com.fmmobile.upcomingmovieskmm.data.datasource.source.remote

import com.fmmobile.upcomingmovieskmm.data.datasource.MovieDataSource
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieModel
import com.fmmobile.upcomingmovieskmm.data.datasource.source.remote.response.GenreListResponse
import com.fmmobile.upcomingmovieskmm.data.datasource.source.remote.response.MovieListResponse
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class MovieRemoteDataSource(
    private val service: NetworkServices = NetworkServices()
): MovieDataSource {
    override suspend fun    getGenres(): Result<GenreListResponse> {
        return service.run(EndPoint.Genre)
    }

    override suspend fun getMovies(page: Int): Result<MovieListResponse> {
        val params = mapOf("page" to page.toString())
        return service.run(EndPoint.Upcoming, params)
    }

    override suspend fun saveMovie(movie: IMovieModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getMovie(movieId: Long): IMovieModel? {
        TODO("Not yet implemented")
    }

    override suspend fun removeMovie(movie: IMovieModel) {
        TODO("Not yet implemented")
    }
}