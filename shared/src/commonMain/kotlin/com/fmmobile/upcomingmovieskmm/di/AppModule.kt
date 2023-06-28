package com.fmmobile.upcomingmovieskmm.di

import com.fmmobile.upcomingmovieskmm.domain.implementation.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmm.domain.implementation.MovieRepositoryImpl
import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmm.data.implementation.MovieRealmDataSource
import com.fmmobile.upcomingmovieskmm.data.source.remote.NetworkServices
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::GenreRepositoryImpl)
    singleOf(::MovieRepositoryImpl)
}

val remoteModule = module {
    singleOf(::MovieRemoteDataSource)
    singleOf(::NetworkServices)
}

val realmModule = module {
    singleOf(::MovieRealmDataSource)
}