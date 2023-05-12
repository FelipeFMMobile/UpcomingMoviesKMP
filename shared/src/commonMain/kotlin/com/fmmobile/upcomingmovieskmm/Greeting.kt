package com.fmmobile.upcomingmovieskmm
import kotlinx.datetime.*

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"+
                "\nThere are only ${daysUntilNewYear()} days left until New Year! 🎆"
    }
}