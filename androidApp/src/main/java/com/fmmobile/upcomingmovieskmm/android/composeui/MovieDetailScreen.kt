package com.fmmobile.upcomingmovieskmm.android.composeui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fmmobile.upcomingmovieskmm.network.Api
import com.fmmobile.upcomingmovieskmm.network.model.Movie

@Composable
fun MovieDetailScreen(info: MovieInfo) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Api.imageDomain.domain.plus(info.posterPath))
                .build(),
            contentDescription = info.title,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(width = 150.dp, height = 200.dp)
                .padding(end = 8.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = info.title,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = info.overview,
            style = MaterialTheme.typography.body1
        )
    }
}
