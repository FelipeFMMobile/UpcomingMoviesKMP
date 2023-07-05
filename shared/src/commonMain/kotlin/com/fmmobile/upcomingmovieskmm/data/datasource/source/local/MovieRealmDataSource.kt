package com.fmmobile.upcomingmovieskmm.data.datasource.source.local

import com.fmmobile.upcomingmovieskmm.data.datasource.MovieDataSource
import com.fmmobile.upcomingmovieskmm.data.datasource.source.local.dataModels.MovieListDAO
import com.fmmobile.upcomingmovieskmm.data.datasource.source.local.dataModels.MovieRO
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IGenreListModel
import com.fmmobile.upcomingmovieskmm.data.datasource.models.IMovieModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmResults

class MovieRealmDataSource: MovieDataSource {

    private val realm: Realm by lazy {
        val configuration = RealmConfiguration.create(schema = setOf(MovieRO::class))
        Realm.open(configuration)
    }

    override suspend fun getGenres(): Result<IGenreListModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovies(page: Int): Result<MovieListDAO> {
        val pageSize = 10
        val movieList = MovieListDAO()
        val movies: RealmResults<MovieRO> = realm.query(MovieRO::class).find()

        movies.mapIndexed { index, movie ->
            val start = (page -1) * pageSize
            if (index >= start && index < start+pageSize) {
                movieList.results.add(movie)
            }
        }
        return  Result.success(movieList)
    }

    override suspend fun saveMovie(movie: IMovieModel) {
        realm.write {
            this.copyToRealm(MovieRO().apply {
                adult  = movie.adult
                backdropPath = movie.backdropPath
                genreIDS = movie.genreIDS
                genreIDSString = movie.genreIDS.joinToString(",")
                id = movie.id
                originalLanguage = movie.originalLanguage
                originalTitle = movie.originalTitle
                overview = movie.overview
                popularity = movie.popularity
                posterPath = movie.posterPath
                releaseDate = movie.releaseDate
                title = movie.title
                video = movie.video
                voteAverage = movie.voteAverage
                voteCount = movie.voteCount
            })
        }
    }

    override suspend fun getMovie(movieId: Long): IMovieModel? {
        val movie: MovieRO? = realm
            .query<MovieRO>(MovieRO::class,"id == $0", movieId)
            .first()
            .find()
        return movie
    }

    override suspend fun removeMovie(movie: IMovieModel) {
        realm.write {
            val movieRO: MovieRO = this
                .query<MovieRO>(MovieRO::class,"id == $0", movie.id)
                .find().first()
            delete(movieRO)
        }
    }
}