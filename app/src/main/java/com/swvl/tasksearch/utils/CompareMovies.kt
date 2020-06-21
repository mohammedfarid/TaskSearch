package com.swvl.tasksearch.utils

import com.swvl.tasksearch.model.movieresponse.MoviesItem

object CompareMovies : Comparator<MoviesItem?> {
    override fun compare(o1: MoviesItem?, o2: MoviesItem?): Int = when {
        o1?.year != o2?.year -> (o1?.year ?: 0) - (o2?.year ?: 0)
        o1?.rating != o2?.rating -> (o1?.rating ?: 0) - (o2?.rating ?: 0)
        else -> (o1?.year ?: 0) - (o2?.year ?: 0)
    }

}