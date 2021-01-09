package com.test.movieslist.network

import com.test.movieslist.BuildConfig
import com.test.movieslist.models.moviedetailsmodels.MovieDetailsModel
import com.test.movieslist.models.movieslistmodels.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET(BuildConfig.DISCOVER_MOVIE_URL)
    fun getMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET(BuildConfig.MOVIE_DETAILS_URL)
    fun getMovieDetails(@Path("movie_id") movieId: Int): Single<MovieDetailsModel>

}