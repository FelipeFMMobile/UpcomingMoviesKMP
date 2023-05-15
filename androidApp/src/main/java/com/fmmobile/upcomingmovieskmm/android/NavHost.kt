package com.fmmobile.upcomingmovieskmm.android

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fmmobile.upcomingmovieskmm.android.composeui.MovieDetailScreen
import com.fmmobile.upcomingmovieskmm.android.composeui.MovieInfo
import com.fmmobile.upcomingmovieskmm.android.composeui.MovieListMain


object NavGraph {
    const val MovieList = "movieList"
    const val MovieDetail = "movieDetail/{info}"
}

sealed class NavActions(val route: String) {
    object MovieList : NavActions(NavGraph.MovieList)
    //data class MovieDetail(val info: MovieInfo) : NavActions(NavGraph.MovieDetail)
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
            route = NavGraph.MovieDetail,
            arguments = listOf(navArgument("info") { type =
                NavType.StringType
            })
        ) {
            val info = it.arguments?.getString("info")

            info?.let {
                val movie = MovieInfo.fromUriString(info)
                MovieDetailScreen(info = movie)
            }
        }
    }
}
