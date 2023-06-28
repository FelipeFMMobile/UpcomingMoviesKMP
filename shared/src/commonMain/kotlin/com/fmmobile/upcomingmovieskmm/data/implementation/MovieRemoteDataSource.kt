package com.fmmobile.upcomingmovieskmm.data.implementation

import com.fmmobile.upcomingmovieskmm.data.datasource.MovieDataSource
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieListModel
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieModel
import com.fmmobile.upcomingmovieskmm.data.source.response.GenreListResponse
import com.fmmobile.upcomingmovieskmm.data.source.response.MovieListResponse
import com.fmmobile.upcomingmovieskmm.data.source.remote.EndPoint
import com.fmmobile.upcomingmovieskmm.data.source.remote.NetworkServices
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class MovieRemoteDataSource(
    private val service: NetworkServices = NetworkServices()
): MovieDataSource {
    override suspend fun getGenres(): Result<GenreListResponse> {
        return service.run(EndPoint.genre)
    }

    override suspend fun getMovies(page: Int): Result<MovieListResponse> {
        val params = mapOf("page" to page.toString())
        return service.run(EndPoint.upcoming, params)
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

    suspend fun isOn() : Boolean {
        val response = service.httpClient.get("https://www.google.com")
        if (response.status == HttpStatusCode.OK) {
            return true
        }
        return  false
    }
}