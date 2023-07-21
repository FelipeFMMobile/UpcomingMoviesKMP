package com.fmmobile.upcomingmovieskmm.data.datasource.source.remote

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
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
        this.expectSuccess = true
    }

    public fun make(endpoint: EndPoint, params: Map<String, String>? = null) : Url {
        val builder = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = Api.Prod.domain
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

    public suspend inline fun <reified T>
            run(endpoint: EndPoint, params: Map<String, String>? = null) : Result<T> {
        return try {
            val response = httpClient.get(make(endpoint, params))
            Result.success(response.body())
        } catch (e: ClientRequestException) {
            Result.failure(e)
        } catch (e: ServerResponseException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: SerializationException) {
            Result.failure(e)
        }
    }
}