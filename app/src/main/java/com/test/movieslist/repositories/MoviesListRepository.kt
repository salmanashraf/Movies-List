package com.test.movieslist.repositories

import com.test.movieslist.models.movieslistmodels.MovieResponse
import com.test.movieslist.network.MainApiClient
import com.test.movieslist.callbacks.ResultCallBack
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception

class MoviesListRepository private constructor() {

    var output = ResultCallBack<MovieResponse, String>()

    companion object {
        private var mInstance: MoviesListRepository? = null
        fun getInstance(): MoviesListRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = MoviesListRepository()
                }
            }
            return mInstance!!
        }
    }

    fun getMoviesList(movieDisposable: CompositeDisposable, currentPage: Int) {
        try {
            movieDisposable.add(
                MainApiClient.getMoviesApi()
                    .getMovies(currentPage)
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

    private fun completed(movieList: MovieResponse) {
        output.complete.postValue(movieList)
    }

}
