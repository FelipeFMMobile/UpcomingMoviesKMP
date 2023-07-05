package com.fmmobile.upcomingmovieskmm.data.datasource.source.local.dataModels

import com.fmmobile.upcomingmovieskmm.data.datasource.models.IDatesModel
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieListModel
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

class MovieListDAO: IMovieListModel {
    override val dates: DatesDAO = DatesDAO()

    override val page: Long = 0

    override val results: MutableList<MovieRO> = mutableListOf()

    override val totalPages: Long = 0

    override val totalResults: Long = 0
}


class DatesDAO: IDatesModel {
    override val maximum: String = ""
    override val minimum: String = ""
}
