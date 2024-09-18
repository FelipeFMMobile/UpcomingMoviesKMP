package com.fmmobile.upcomingmovieskmp.android.di

import com.fmmobile.upcomingmovieskmp.data.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmp.data.MovieRepositoryImpl
import com.fmmobile.upcomingmovieskmp.domain.usecase.GetMovieListUseCase
import org.koin.core.component.KoinComponent
import org.koin.dsl.module
import org.koin.core.component.inject

class AndroidModule : KoinComponent {
    val module = module {
        val genreRepository : GenreRepositoryImpl by inject()
        val movieRepository : MovieRepositoryImpl by inject()
        single { GetMovieListUseCase(genreRepository, movieRepository) }
    }
    val getMovieListUseCase : GetMovieListUseCase by inject()
}
