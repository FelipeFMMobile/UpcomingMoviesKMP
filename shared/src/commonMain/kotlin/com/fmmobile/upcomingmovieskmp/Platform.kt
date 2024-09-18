package com.fmmobile.upcomingmovieskmp

import com.fmmobile.upcomingmovieskmp.di.appModule
import com.fmmobile.upcomingmovieskmp.di.realmModule
import com.fmmobile.upcomingmovieskmp.di.remoteModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// start a KoinApplication in Global context
fun appModule() = listOf(appModule, remoteModule, realmModule)

fun debugBuild() {
    Napier.base(DebugAntilog())
}