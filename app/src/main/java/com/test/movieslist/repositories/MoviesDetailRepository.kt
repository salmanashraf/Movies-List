package com.test.movieslist.repositories

import com.test.movieslist.models.moviedetailsmodels.MovieDetailsModel
import com.test.movieslist.network.MainApiClient
import com.test.movieslist.callbacks.ResultCallBack
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception

class MoviesDetailRepository private constructor() {

    var output = ResultCallBack<MovieDetailsModel, String>()

    companion object {
        private var mInstance: MoviesDetailRepository? = null
        fun getInstance(): MoviesDetailRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = MoviesDetailRepository()
                }
            }
            return mInstance!!
        }
    }

    fun getMovies(movieDisposable: CompositeDisposable, movieID: Int) {
        try {
            movieDisposable.add(
                MainApiClient.getMoviesApi()
                    .getMovieDetails(movieID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { getMovieDetails ->
                            completed(getMovieDetails)
                        }, {
                            Timber.d(it)
                        })
            )

        } catch (e: Exception) {
            Timber.d(e.message.toString())
        }
    }

    private fun completed(movieDetailModel: MovieDetailsModel) {
        output.complete.postValue(movieDetailModel)
    }

}
