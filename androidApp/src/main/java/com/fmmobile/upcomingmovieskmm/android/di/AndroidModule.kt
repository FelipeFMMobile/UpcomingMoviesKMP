package com.fmmobile.upcomingmovieskmm.android.di

import com.fmmobile.upcomingmovieskmm.data.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmm.data.MovieRepositoryImpl
import com.fmmobile.upcomingmovieskmm.domain.usecase.GetMovieListUseCase
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
