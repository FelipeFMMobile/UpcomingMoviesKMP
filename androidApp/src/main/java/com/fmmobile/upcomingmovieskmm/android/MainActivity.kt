package com.fmmobile.upcomingmovieskmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fmmobile.upcomingmovieskmm.network.Api
import com.fmmobile.upcomingmovieskmm.network.GetMovieListUseCase
import com.fmmobile.upcomingmovieskmm.network.model.Movie

class MainActivity : ComponentActivity() {
    var pageCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var movies = remember { mutableStateListOf<Movie>()}
                    var isLoading = remember { mutableStateOf(false)}
                    LaunchedEffect(true) {
                        try {
                            movies.addAll(GetMovieListUseCase()
                                .loadMovies(pageCount).results)
                        } catch (e: Exception) {
                            e.localizedMessage ?: "error"
                        }
                    }
                    MovieList(
                        movies = movies,
                        isLoading = isLoading
                    ) {
                        pageCount += 1
                        isLoading.value = true
                        try {
                            movies.addAll(
                                GetMovieListUseCase()
                                    .loadMovies(pageCount).results
                            )
                            isLoading.value = false
                        } catch (e: Exception) {
                            e.localizedMessage ?: "error"
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Composable
fun MovieList(
    movies: List<Movie>,
    isLoading: MutableState<Boolean>,
    loadNextPage: suspend () -> Unit) {
    fun getLimitedLinesText(text: String, maxLines: Int): String {
        val lines = text.lines()
        if (lines.size <= maxLines) {
            return text
        }
        val limitedLines = lines.subList(0, maxLines)
        return limitedLines.joinToString(separator = "\n")
    }
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
        ) {
        items(movies) { movie ->
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Api.imageDomain.domain.plus(movie.posterPath))
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
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        val movies = listOf(
            Movie(
                title = "Movie 1",
                posterPath = "https://www.example.com/movie1_poster.jpg",
                overview = "This is the overview of Movie 1",
                releaseDate = "2022-01-01",
                adult = false,
                video = false,
                voteAverage = 100.0,
                voteCount = 0,
                backdropPath = null,
                genreIDS = emptyList(),
                originalLanguage = "",
                originalTitle = "",
                popularity = 0.0,
                id = 100
            ),
        )
        var loading = remember { mutableStateOf(false)}
        MovieList(movies, loading) {

        }
    }
}
