package com.test.movieslist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.movieslist.models.movieslistmodels.MovieResponse
import com.test.movieslist.repositories.MoviesListRepository
import com.test.movieslist.callbacks.ResultCallBack
import com.test.movieslist.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class MoviesListViewModel(private val app: Application) : AndroidViewModel(app) {
    var callBack = ResultCallBack<MovieResponse, String>()
    private val compositeDisposable = CompositeDisposable()

    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()

    private var mRepository = MoviesListRepository.getInstance()

    fun fetchMovieList(currentPage: Int){
        if (NetworkHelper.isOnline(app.baseContext)) {
            var mRepository = MoviesListRepository.getInstance()
            mRepository.getMoviesList(compositeDisposable, currentPage)
        } else {
            mShowNetworkError.value = true
        }
    }


    init {
        callBack = mRepository.output
    }

}