package com.fmmobile.upcomingmovieskmm.domain.repository

import com.fmmobile.upcomingmovieskmm.domain.model.GenreList

interface GenreRepository {
    suspend fun getGenres(): GenreList
}