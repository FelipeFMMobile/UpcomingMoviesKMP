package com.fmmobile.upcomingmovieskmm.network

enum class EndPoint(val path: String) {
    genre("/3/genre/movie/list"),
    upcoming("/3/movie/upcoming")
}

enum class Api(val domain: String) {
    prod("api.themoviedb.org"),
    imageDomain("https://image.tmdb.org/t/p/w185/")
}