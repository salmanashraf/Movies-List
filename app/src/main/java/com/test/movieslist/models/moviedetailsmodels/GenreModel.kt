package com.test.movieslist.models.moviedetailsmodels

import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("name")
    val genreName: String
)