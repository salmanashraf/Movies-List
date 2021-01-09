package com.test.movieslist.models.movieslistmodels

import com.google.gson.annotations.SerializedName

class MovieModel {

    @SerializedName("adult")
    val adult: Boolean = false

    @SerializedName("backdrop_path")
    val backdropPath: Any = ""

    @SerializedName("genre_ids")
    val genreIds: List<Int> = ArrayList<Int>()

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("original_language")
    val originalLanguage: String = ""

    @SerializedName("original_title")
    val originalTitle: String = ""

    @SerializedName("overview")
    val overview: String = ""

    @SerializedName("popularity")
    val popularity: Double = 0.0

    @SerializedName("poster_path")
    val posterPath: String = ""

    @SerializedName("release_date")
    val releaseDate: String = ""

    @SerializedName("title")
    val title: String = ""

    @SerializedName("video")
    val video: Boolean = false

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0

    @SerializedName("vote_count")
    val voteCount: Double = 0.0
}