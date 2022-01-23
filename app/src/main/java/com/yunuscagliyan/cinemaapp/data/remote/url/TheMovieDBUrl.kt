package com.yunuscagliyan.cinemaapp.data.remote.url


//base url
const val BASE_URL="https://api.themoviedb.org/3/"
const val POSTER_IMAGE_URL="https://image.tmdb.org/t/p/w342"

const val API_KEY = "4ceba0985010b11eb871640206d56895"

const val PER_PAGE_ITEM=20

///paths
const val GET_UP_COMING_MOVIE = "movie/upcoming"
const val GET_POPULAR_MOVIE = "movie/popular"
const val GET_TRENDING_MOVIE_DAY = "trending/movie/day"
const val GET_TOP_RATED_MOVIE = "movie/top_rated"


//movie detail page
const val GET_MOVIE_DETAIL="movie/{movie_id}"
const val GET_MOVIE_CREDITS_LIST="movie/{movie_id}/credits"
const val GET_SIMILAR_MOVIE = "movie/{movie_id}/similar"
const val GET_GENRE_LIST="genre/movie/list"
const val GET_MOVIE_VIDEOS="movie/{movie_id}/videos"
const val GET_CREDIT_DETAIL="credit/{credit_id}"
