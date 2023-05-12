package com.fmmobile.upcomingmovieskmm.network

import com.fmmobile.upcomingmovieskmm.network.model.GenreList
import com.fmmobile.upcomingmovieskmm.network.model.MovieList
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// TODO: Convert to Repository futter (data-domain)
class Service {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getGenres(): GenreList {
        return httpClient.get(make(EndPoint.genre)).body()
    }

    suspend fun getMovies(page: Int): MovieList {
        val params = mapOf("page" to page.toString())
        return httpClient.get(make(EndPoint.upcoming, params)).body()
    }


    private fun make(endpoint: EndPoint, params: Map<String, String>? = null) : Url {
        val builder = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = Api.prod.domain
            encodedPath = endpoint.path
            parameters.apply {
                append("api_key", "2c767653e58ac61f2ef293863e254f5d")
                params?.keys?.forEach {
                    append(it, params.get(it) ?: "")
                }
            }
        }
        return builder.build()
    }
}