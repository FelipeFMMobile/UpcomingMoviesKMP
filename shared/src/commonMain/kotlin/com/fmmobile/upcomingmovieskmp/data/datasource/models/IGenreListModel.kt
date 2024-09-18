package com.fmmobile.upcomingmovieskmp.data.datasource.models

interface IGenreListModel {
    val genres: List<IGenreModel>
}

interface IGenreModel {
    val id: Long
    val name: String
}
