package com.test.movieslist.ui.movies.adapters

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.movieslist.R
import com.test.movieslist.models.movieslistmodels.MovieModel
import com.test.movieslist.ui.movies.adapters.viewHolders.MovieViewHolder
import com.test.movieslist.ui.movies.adapters.viewHolders.LoadingViewHolder


class MovieListAdapter(activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1
    val mActivity: Activity = activity
    private var movieModelList: MutableList<MovieModel> = ArrayList()
    private var isLoadingAdded: Boolean = false
    var onItemClicked: ((MovieModel?) -> Unit)? = null



    fun setMovies(movieModelList: MutableList<MovieModel>) {
        this.movieModelList = movieModelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM -> {
                val item = inflater.inflate(R.layout.movie_items, parent, false)
                MovieViewHolder(item, onItemClicked!!)
            }
            LOADING -> {
                val item = inflater.inflate(R.layout.load_more_progress, parent, false)
                LoadingViewHolder(item)
            }
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }

    override fun getItemCount(): Int {
        return movieModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movieModelList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val movie = movieModelList[position]
                (holder as MovieViewHolder).setMovie(movie)

            }
            LOADING -> {
            }
        }
    }

    fun add(r: MovieModel) {
        movieModelList.add(r)
        notifyItemInserted(movieModelList.size - 1)
    }

    fun addAll(moviesModels: List<MovieModel>) {
        for (result in moviesModels) {
            add(result)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(MovieModel())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = movieModelList.size - 1
        val result = getItem(position)

        if (result != null) {
            movieModelList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): MovieModel? {
        return movieModelList.get(position)
    }
}