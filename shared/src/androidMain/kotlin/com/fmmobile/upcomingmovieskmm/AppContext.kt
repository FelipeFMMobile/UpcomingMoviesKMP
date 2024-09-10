package com.fmmobile.upcomingmovieskmm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

object AppContext : Application.ActivityLifecycleCallbacks {
    private lateinit var application: Application
    private var activityRef: WeakReference<Activity>? = null
    fun setUp(context: Context) {
        application = context as Application
        application.registerActivityLifecycleCallbacks(this)
    }

    fun get(): Context {
        if (::application.isInitialized.not())
            throw Exception("Application context isn't initialized")
        return application.applicationContext
    }

    fun getActivity(): Activity? {
        return activityRef?.get()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
       this.activityRef = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        this.activityRef = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        activityRef?.clear()
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        activityRef?.clear()
    }
}
