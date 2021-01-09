package com.test.movieslist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.movieslist.models.moviedetailsmodels.MovieDetailsModel
import com.test.movieslist.repositories.MoviesDetailRepository
import com.test.movieslist.callbacks.ResultCallBack
import com.test.movieslist.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class MoviesDetailViewModel(private val app: Application) : AndroidViewModel(app) {
    var callBack = ResultCallBack<MovieDetailsModel, String>()
    private val compositeDisposable = CompositeDisposable()

    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()

    private var mRepository = MoviesDetailRepository.getInstance()

    fun fetchMovieDetail(movieID: Int){
        if (NetworkHelper.isOnline(app.baseContext)) {
            var mRepository = MoviesDetailRepository.getInstance()
            mRepository.getMovies(compositeDisposable, movieID)
        } else {
            mShowNetworkError.value = true
        }
    }


    init {
        callBack = mRepository.output
    }

}