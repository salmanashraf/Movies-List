package com.test.movieslist.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.movieslist.R
import com.test.movieslist.models.movieslistmodels.MovieModel
import com.test.movieslist.ui.moviedetail.MovieDetailActivity
import com.test.movieslist.ui.moviedetail.MovieDetailActivity.Companion.REQUEST_MOVIE_ID
import com.test.movieslist.ui.movies.adapters.MovieListAdapter
import com.test.movieslist.utils.recyclerView.PaginationScrollListener
import com.test.movieslist.utils.recyclerView.SpacesItemDecoration
import com.test.movieslist.viewmodels.MoviesListViewModel
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    private lateinit var rvList: RecyclerView
    private lateinit var tvNoRecords: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var movieListAdapter: MovieListAdapter
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = 1
   // private val totalPages: Int = 3
    private lateinit var mViewModel: MoviesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        rvList = findViewById(R.id.moviesRecyclerView)
        tvNoRecords = findViewById(R.id.error)
        progressBar = findViewById(R.id.progress_circular)

        initializeObserver()
        setRecyclerView()
        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            currentPage = 1
            getMovies(currentPage)
        })
        progressBar.visibility = View.VISIBLE
        getMovies(currentPage)

    }

    private fun initializeObserver() {
        mViewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
        mViewModel.callBack.complete.observe(this, {
            setMovies(it.movieModels)
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            hideProgressBar()
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }

    private fun setRecyclerView() {
        var mGridLayoutManager = GridLayoutManager(this, 2)
        rvList.layoutManager = mGridLayoutManager
        rvList.addItemDecoration(SpacesItemDecoration(1))
        rvList.addOnScrollListener(object : PaginationScrollListener(mGridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                getMovies(currentPage)
            }
        })

        rvList.setHasFixedSize(true)

        movieListAdapter = MovieListAdapter(this)
        rvList.adapter = movieListAdapter


        //RecycleView Click Listener
        movieListAdapter.onItemClicked = {
            it?.let { movieModel ->
                startActivity(
                        Intent(this, MovieDetailActivity::class.java)
                                .putExtra(REQUEST_MOVIE_ID, movieModel.id))

            }
        }

    }

    private fun getMovies(currentPage: Int) {
        mViewModel.fetchMovieList(currentPage)

    }

    private fun setMovies(moviesList: ArrayList<MovieModel>) {

        if (currentPage == 1) {
            movieListAdapter.setMovies(moviesList)
            movieListAdapter.addLoadingFooter()
            movieListAdapter.notifyDataSetChanged()
//            if (currentPage == totalPages) {
//                movieListAdapter.removeLoadingFooter()
//                isLastPage = true
//            }
        } else {
            if (moviesList.isNotEmpty()) {
                movieListAdapter.removeLoadingFooter()
                isLoading = false
                movieListAdapter.addAll(moviesList)
                movieListAdapter.addLoadingFooter()
            } else {
                movieListAdapter.removeLoadingFooter()
                isLoading = false
                isLastPage = true
            }
        }
    }
    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
        swipeContainer.isRefreshing = false;
    }
}
