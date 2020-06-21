package com.swvl.tasksearch.retrofit.home

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.swvl.tasksearch.model.photoresponse.PhotoListResponse
import com.swvl.tasksearch.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface HomeInterface {
    @GET("rest/")
    suspend fun getImageList(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("nojsoncallback") notJsonCallBack: String,
        @Query("text") movie_name: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String
    ): Response<PhotoListResponse>

    companion object {
        fun create(): HomeInterface {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val gson = GsonBuilder().setLenient().create()
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(Constants.TIME, TimeUnit.MINUTES)
                .writeTimeout(Constants.TIME, TimeUnit.MINUTES)
                .connectTimeout(Constants.TIME, TimeUnit.MINUTES)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
                .create(HomeInterface::class.java)
        }
    }
}