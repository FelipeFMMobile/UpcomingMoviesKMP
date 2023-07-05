package com.fmmobile.upcomingmovieskmm.di

import com.fmmobile.upcomingmovieskmm.data.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmm.data.MovieRepositoryImpl
import com.fmmobile.upcomingmovieskmm.data.datasource.source.remote.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmm.data.datasource.source.local.MovieRealmDataSource
import com.fmmobile.upcomingmovieskmm.data.datasource.source.remote.NetworkServices
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