package com.fmmobile.upcomingmovieskmp.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.fmmobile.upcomingmovieskmp.AppContext
import com.fmmobile.upcomingmovieskmp.android.di.AndroidModule
import com.fmmobile.upcomingmovieskmp.appModule
import com.fmmobile.upcomingmovieskmp.debugBuild
import com.fmmobile.upcomingmovieskmp.domain.repository.LocationCallback
import com.fmmobile.upcomingmovieskmp.domain.repository.LocationInfo
import com.fmmobile.upcomingmovieskmp.domain.usecase.GetMovieListUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity(){
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            AppContext.setUp(applicationContext)
            androidContext(this@MainActivity)
            androidLogger()
            modules(appModule() + AndroidModule().module)
            debugBuild()
        }
        setContent {
            val navController = rememberNavController()
            val showLocationAlert = remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()
            val location = remember {
                mutableStateOf(LocationInfo(0.0, 0.0))
            }

            MyApplicationTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Upcoming Movies KMP")
                            }, navigationIcon = {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                }
                            },
                            actions = {
                                // Adding the location icon on the right side of the top bar
                                IconButton(onClick = {
                                        coroutineScope.launch {
                                        try {
                                            val useCase =
                                                AndroidModule().getMovieListUseCase
                                            val callback = object : LocationCallback {
                                                override fun onLocationReceived(locationInfo: LocationInfo) {
                                                    location.value = locationInfo
                                                    showLocationAlert.value = true
                                                    Log.i("Location", "User location: $location")
                                                }
                                                override fun onLocationError(error: Throwable) {
                                                    Log.e("Location", "error getting location ${error.localizedMessage}")
                                                }

                                            }
                                            useCase.getLocation(callback)
                                        } catch (e: Exception) {
                                            Log.e(
                                                "Location",
                                                "Failed to get location: ${e.localizedMessage}"
                                            )
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                    }
                ) {
                    MovieNavigation(navController = navController)
                }
            }
            // AlertDialog to show when the location is updated
            if (showLocationAlert.value) {
                AlertDialog(
                    onDismissRequest = { showLocationAlert.value = false },
                    title = {
                        Text(text = "Location Update ${location.value.lat}, ${location.value.long}")
                    },
                    text = {
                        Text("Location updated")
                    },
                    confirmButton = {
                        TextButton(onClick = { showLocationAlert.value = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}
