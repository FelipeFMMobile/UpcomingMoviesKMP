package com.fmmobile.upcomingmovieskmm

import com.fmmobile.upcomingmovieskmm.di.appModule
import com.fmmobile.upcomingmovieskmm.di.realmModule
import com.fmmobile.upcomingmovieskmm.di.remoteModule
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