package com.fmmobile.upcomingmovieskmm

import com.fmmobile.upcomingmovieskmm.domain.implementation.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmm.domain.implementation.MovieRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(appModule())
    }
}

class InjectionHelper : KoinComponent {
    val genreRepository : GenreRepositoryImpl by inject()
    val movieRepository : MovieRepositoryImpl by inject()
}