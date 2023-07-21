package com.fmmobile.upcomingmovieskmm.android

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fmmobile.upcomingmovieskmm.android.composeui.MovieDetailScreen
import com.fmmobile.upcomingmovieskmm.android.composeui.MovieListMain

object NavGraph {
    const val MovieList = "movieList"
    const val MovieDetail = "movieDetail"
}

sealed class NavActions(val route: String) {
    object MovieList : NavActions(NavGraph.MovieList)
    object MovieDetail : NavActions(NavGraph.MovieDetail)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MovieNavigation(modifier: Modifier = Modifier,
            navController: NavHostController,
            startDestination: String = NavActions.MovieList.route) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavActions.MovieList.route) {
            MovieListMain(navController)
        }
        composable(
            route = NavGraph.MovieDetail
        ) {
            MovieDetailScreen()
        }
    }
}
