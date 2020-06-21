package com.swvl.tasksearch.retrofit.home

import com.swvl.tasksearch.model.photoresponse.PhotoListResponse
import com.swvl.tasksearch.utils.Constants
import retrofit2.Response

class HomeRepository(var homeInterface: HomeInterface) {

    suspend fun getImageList(
        movieName: String,
        page: String
    ): Response<PhotoListResponse> {
        return homeInterface.getImageList(
            method = Constants.METHOD,
            apiKey = Constants.API_KEY,
            format = Constants.JSON,
            notJsonCallBack = Constants.NO_JSON_CALL_BACK,
            movie_name = movieName,
            page = page,
            perPage = Constants.PER_PAGE
        )
    }
}