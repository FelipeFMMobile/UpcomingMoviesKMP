package com.fmmobile.upcomingmovieskmm.data.source.remote

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

class NetworkServices {
    public val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    public fun make(endpoint: EndPoint, params: Map<String, String>? = null) : Url {
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

    public suspend inline fun <reified T> run(endpoint: EndPoint,
                                              params: Map<String, String>? = null) : T {
        return httpClient.get(make(endpoint, params)).body()
    }
}