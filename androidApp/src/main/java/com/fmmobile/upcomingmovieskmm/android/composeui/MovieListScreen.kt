package com.fmmobile.upcomingmovieskmm.android.composeui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fmmobile.upcomingmovieskmm.android.NavActions
import com.fmmobile.upcomingmovieskmm.android.di.AndroidModule
import com.fmmobile.upcomingmovieskmm.domain.usecase.GetMovieListUseCase
import com.fmmobile.upcomingmovieskmm.domain.model.Movie

@Composable
fun MovieListMain(navController: NavHostController,
                  useCase: GetMovieListUseCase =
                      AndroidModule().getMovieListUseCase
    ) {
    var pageCount = 1
    val movies = remember { mutableStateListOf<Movie>() }
    val isLoading = remember { mutableStateOf(false)}
    LaunchedEffect(true) {
        try {
            movies.addAll(
                useCase.loadMovies(pageCount).results
            )
            val info = useCase.getLocation()
            Log.i("Location","Last user location$info")
        } catch (e: Exception) {
            e.localizedMessage ?: "error"
        }
    }
    MovieListScreen(
        useCase,
        navController,
        movies = movies,
        isLoading = isLoading
    ) {
        pageCount += 1
        isLoading.value = true
        try {
            movies.addAll(
                useCase.loadMovies(pageCount).results
            )
            isLoading.value = false
        } catch (e: Exception) {
            e.localizedMessage ?: "error"
        }
    }
}

@Composable
fun MovieListScreen(
    useCase: GetMovieListUseCase = AndroidModule().getMovieListUseCase,
    navController: NavHostController,
    movies: List<Movie>,
    isLoading: MutableState<Boolean>,
    loadNextPage: suspend () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(movies) { movie ->
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        movie.let {
                            useCase.selectMovie(it)
                            navController.navigate(NavActions.MovieDetail.route)
                        }
                    },
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterPath)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(width = 150.dp, height = 200.dp)
                        .padding(end = 8.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.Top)) {
                    Text(text = movie.title, style = MaterialTheme.typography.h6)
                    Text(text =  movie.overview,
                        style = MaterialTheme.typography.body2,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis)
                    Text(text = movie.releaseDate, style = MaterialTheme.typography.caption)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            LaunchedEffect(listState) {
                if (movies.indexOf(movie) == movies.lastIndex && !isLoading.value) {
                    loadNextPage()
                }
            }
        }
        if (isLoading.value) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
