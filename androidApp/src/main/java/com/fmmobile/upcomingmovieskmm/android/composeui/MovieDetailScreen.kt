package com.fmmobile.upcomingmovieskmm.android.composeui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fmmobile.upcomingmovieskmm.android.di.AndroidModule
import com.fmmobile.upcomingmovieskmm.data.source.remote.Api
import com.fmmobile.upcomingmovieskmm.domain.model.Movie
import com.fmmobile.upcomingmovieskmm.domain.usecase.GetMovieListUseCase
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(useCase: GetMovieListUseCase = AndroidModule().getMovieListUseCase) {
    val coroutineScope = rememberCoroutineScope()
    val isSavedState = remember { mutableStateOf(false) }
    val movie = useCase.selectedMovie
    LaunchedEffect(true) {
        movie?.let {
            isSavedState.value = useCase.isSaved(it)
        }
    }
    val buttonColors = if (isSavedState.value) {
        ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    } else {
        ButtonDefaults.buttonColors(backgroundColor = Color.Green)
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie?.posterPath)
                .build(),
            contentDescription = movie?.title,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(width = 150.dp, height = 200.dp)
                .padding(end = 8.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie?.title ?: "",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie?.overview ?: "",
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            coroutineScope.launch {
                movie?.let {
                    if (isSavedState.value) {
                        useCase.removeMovie(movie)
                        isSavedState.value = false
                    } else {
                        useCase.saveMovie(movie)
                        isSavedState.value = true
                    }
                }
            }
        },
            colors = buttonColors,
            modifier = Modifier
            .width(150.dp)
            .height(50.dp)
            .padding(8.dp)) {
            Text(
                text = if (isSavedState.value) "My Favorite" else "Favorite this",
                style = MaterialTheme.typography.body1
            )
        }
    }
}