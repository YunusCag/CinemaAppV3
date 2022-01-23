package com.yunuscagliyan.cinemaapp.data.remote.model.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieModel>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)