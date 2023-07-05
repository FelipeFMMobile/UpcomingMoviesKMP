package com.fmmobile.upcomingmovieskmm

import com.fmmobile.upcomingmovieskmm.data.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmm.data.MovieRepositoryImpl
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(appModule())
    }
}

fun debugMode() {
    Napier.base(DebugAntilog())
}

class InjectionHelper : KoinComponent {
    val genreRepository : GenreRepositoryImpl by inject()
    val movieRepository : MovieRepositoryImpl by inject()
}