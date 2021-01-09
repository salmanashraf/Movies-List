package com.test.movieslist.ui.moviedetail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.c.progress_dialog.BlackProgressDialog
import com.test.movieslist.BuildConfig
import com.test.movieslist.R
import com.test.movieslist.models.moviedetailsmodels.GenreModel
import com.test.movieslist.models.moviedetailsmodels.LanguageModel
import com.test.movieslist.models.moviedetailsmodels.MovieDetailsModel
import com.test.movieslist.viewmodels.MoviesDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_MOVIE_ID = "MovieId"
    }

    val weburl = "https://www.themoviedb.org/movie/"

    private lateinit var mViewModel: MoviesDetailViewModel
    private var movieID: Int = 0
    lateinit var dialog: BlackProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movieID = intent.getIntExtra(REQUEST_MOVIE_ID, 0)
        initializeObserver()

        dialog = BlackProgressDialog(this, getString(R.string.loading))
        dialog.setCancelable(false)
        dialog.show()
        mViewModel.fetchMovieDetail(movieID)

    }

    private fun initializeObserver() {
        mViewModel = ViewModelProvider(this).get(MoviesDetailViewModel::class.java)
        mViewModel.callBack.complete.observe(this, {
            setMovieData(it)
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }


    private fun setMovieData(movieDetailsModel: MovieDetailsModel) {
        dialog.hide()
        title = movieDetailsModel?.title
        movietitle.text = movieDetailsModel?.title //update activity title
        movie_release_date.text = movieDetailsModel?.releaseDate
        moviedescription.text = movieDetailsModel?.overview
        runtime.text = String.format("%s min", movieDetailsModel?.runtime)
        setImageView("${BuildConfig.IMAGE_URL}${movieDetailsModel.posterPath}")
        movieGenre.text = movieDetailsModel.genreModels.genreListToString()
        language.text = movieDetailsModel.spokenLanguageModels.languageListToString()

        bookNow.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("$weburl${movieID}")
            startActivity(openURL)
        }
    }

    fun setImageView(url: String) {
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(image)
    }

    fun List<GenreModel>.genreListToString(): String {
        return this.joinToString(", ", transform = { it.genreName })
    }
    fun List<LanguageModel>.languageListToString(): String {
        return this.joinToString(", ", transform = { it.langName })
    }
}
