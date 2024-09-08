package com.fmmobile.upcomingmovieskmm

import android.app.Application
import android.content.Context

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}
actual fun getPlatform(): Platform = AndroidPlatform()