package com.fmmobile.upcomingmovieskmm.android.composeui

import android.net.Uri
import android.os.Parcelable
import android.util.Base64
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieInfo(val title: String, val posterPath: String, val overview: String) : Parcelable {
    fun toUriString(): String {
        return Uri.encode(title.encodeBase64())
            .plus(",")
            .plus(Uri.encode(posterPath.encodeBase64()))
            .plus(",")
            .plus(Uri.encode(overview.encodeBase64()))
    }

    companion object {
        fun fromUriString(uriString: String): MovieInfo {
            return uriString.toMovie()
        }
    }
}

fun String.toMovie(): MovieInfo {
    val parts = split(",").map { Uri.decode(it) }
    return MovieInfo(parts[0].decodeBase64(), parts[1].decodeBase64(), parts[2].decodeBase64())
}

fun String.decodeBase64(): String {
    return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
}

fun String.encodeBase64(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
}