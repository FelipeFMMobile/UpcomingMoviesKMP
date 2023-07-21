package com.fmmobile.upcomingmovieskmm.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.compose.rememberNavController
import com.fmmobile.upcomingmovieskmm.android.di.AndroidModule
import com.fmmobile.upcomingmovieskmm.appModule
import com.fmmobile.upcomingmovieskmm.debugBuild
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(appModule() + AndroidModule().module)
            debugBuild()
        }
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Upcoming Movies KMM")
                            }, navigationIcon = {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                }
                            }
                        )
                    }
                ) {
                    MovieNavigation(navController = navController)
                }
            }
        }
    }
}
