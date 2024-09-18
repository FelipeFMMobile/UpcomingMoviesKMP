package com.fmmobile.upcomingmovieskmp.data.datasource.source.local.dataModels

import com.fmmobile.upcomingmovieskmp.data.datasource.models.IMovieModel
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey

class MovieRO: RealmObject, IMovieModel {
    override var adult: Boolean = false

    override var backdropPath: String? = ""

    @Ignore
    override var genreIDS: List<Long> = realmListOf()

    var genreIDSString = ""

    @PrimaryKey
    override var id: Long = 0

    override var originalLanguage: String = ""

    override var originalTitle: String = ""

    override var overview: String = ""

    override var popularity: Double = 0.0

    override var posterPath: String? = ""

    override var releaseDate: String = ""

    override var title: String = ""

    override var video: Boolean = false

    override var voteAverage: Double = 0.0

    override var voteCount: Long = 0
}