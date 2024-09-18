package com.fmmobile.upcomingmovieskmp

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.lang.ref.WeakReference

interface AppContextResumeCallback {
    fun onPermissionGranted()
    fun onPermissionDenied()
}
object AppContext : Application.ActivityLifecycleCallbacks {
    private lateinit var application: Application
    private var callback: AppContextResumeCallback? = null
    var requestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    fun setUp(context: Context) {
        application = context as Application
        application.registerActivityLifecycleCallbacks(this)
    }

    fun get(): Context {
        if (::application.isInitialized.not())
            throw Exception("Application context isn't initialized")
        return application.applicationContext
    }

    fun registerOnStartedCallback(callback: AppContextResumeCallback) {
        this.callback = callback
    }

    override fun onActivityCreated(activity: Activity,
                                   savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        val componentActivity: ComponentActivity? = activity as? ComponentActivity
        requestPermissionLauncher = componentActivity?.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Handle the permission results
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {
               callback?.onPermissionGranted()
            } else {
                callback?.onPermissionDenied()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity,
                                             outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}
