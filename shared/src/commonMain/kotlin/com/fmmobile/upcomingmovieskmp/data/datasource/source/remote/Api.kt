package com.fmmobile.upcomingmovieskmp.data.datasource.source.remote

enum class EndPoint(val path: String) {
    Genre("/3/genre/movie/list"),
    Upcoming("/3/movie/upcoming")
}

enum class Api(val domain: String) {
    Prod("api.themoviedb.org"),
    ImageDomain("https://image.tmdb.org/t/p/w185/")
}