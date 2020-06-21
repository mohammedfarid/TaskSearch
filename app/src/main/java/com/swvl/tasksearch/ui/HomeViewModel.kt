package com.swvl.tasksearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.swvl.tasksearch.retrofit.home.HomeRepository
import com.swvl.tasksearch.retrofit.home.Result
import kotlinx.coroutines.Dispatchers

class HomeViewModel(var homeRepository: HomeRepository) : ViewModel() {

    fun getMovieList(movieName: String, page: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))
        val result = homeRepository.getImageList(movieName, page)
        try {
            if (result.isSuccessful) {
                emit(Result.success(data = result))
            } else {
                emit(Result.error(message = result.code().toString(), data = null))
            }
        } catch (e: Exception) {
            emit(Result.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}