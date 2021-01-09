package com.test.movieslist.models.moviedetailsmodels

import com.google.gson.annotations.SerializedName

data class LanguageModel(
    @SerializedName("name")
    val langName: String
)