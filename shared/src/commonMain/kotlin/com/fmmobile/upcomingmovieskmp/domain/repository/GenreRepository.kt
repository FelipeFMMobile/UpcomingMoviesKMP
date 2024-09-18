package com.fmmobile.upcomingmovieskmp.domain.repository

import com.fmmobile.upcomingmovieskmp.domain.model.GenreList

interface GenreRepository {
    suspend fun getGenres(): GenreList
}