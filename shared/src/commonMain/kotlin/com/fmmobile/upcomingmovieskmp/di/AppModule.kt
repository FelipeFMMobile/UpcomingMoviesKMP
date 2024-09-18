package com.fmmobile.upcomingmovieskmp.di

import com.fmmobile.upcomingmovieskmp.data.GenreRepositoryImpl
import com.fmmobile.upcomingmovieskmp.data.MovieRepositoryImpl
import com.fmmobile.upcomingmovieskmp.data.datasource.source.remote.MovieRemoteDataSource
import com.fmmobile.upcomingmovieskmp.data.datasource.source.local.MovieRealmDataSource
import com.fmmobile.upcomingmovieskmp.data.datasource.source.remote.NetworkServices
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