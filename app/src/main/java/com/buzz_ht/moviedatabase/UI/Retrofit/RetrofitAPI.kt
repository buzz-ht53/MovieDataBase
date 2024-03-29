package com.buzz_ht.moviedatabase.UI.Retrofit

import com.buzz_ht.moviedatabase.UI.Model.MovieModel
import retrofit2.Response
import retrofit2.http.*


interface RetrofitAPI {

    @GET(".")
    suspend fun getMovie(
        @Query("t") s: String,
        @Query("apikey") apikey: String
    ): Response<MovieModel?>

}