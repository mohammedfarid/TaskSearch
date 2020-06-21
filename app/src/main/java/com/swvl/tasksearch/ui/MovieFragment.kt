package com.swvl.tasksearch.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.swvl.tasksearch.R
import com.swvl.tasksearch.model.movieresponse.MovieListResponse
import com.swvl.tasksearch.model.movieresponse.MoviesItem
import com.swvl.tasksearch.ui.base.BaseFragment
import com.swvl.tasksearch.utils.FragmentUtils
import com.swvl.tasksearch.utils.JsonUtils
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : BaseFragment(), TextWatcher {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var movieRvAdapter: MovieRvAdapter
    var movieListResponse: MovieListResponse? = null
    var movieListSorted: List<MoviesItem?>? = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(R.layout.fragment_movie, contentContainer, true)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListResponse = MovieListResponse()
        movieListResponse = getMovieList()
        movieListSorted =
            movieListResponse?.movies?.sortedWith(compareBy<MoviesItem?> { it?.year }.thenByDescending { it?.rating })
        layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        movieRvAdapter =
            MovieRvAdapter(mContext,
                movieListSorted, movieListSorted, onClickItem = { moviesItem ->
                    FragmentUtils.replaceFragment(
                        activity = activity as HomeActivity,
                        TAG = DetailFragment.TAG,
                        addToBackStack = true,
                        fragment = DetailFragment.newInstant(moviesItem),
                        container = R.id.fragment_container
                    )
                })
        rv_movie.layoutManager = layoutManager
        rv_movie.adapter = movieRvAdapter

        ed_search.addTextChangedListener(this)
    }

    private fun getMovieList(): MovieListResponse? {
        JsonUtils.readJSONFromAsset(mContext, "movies")?.let { result ->
            return Gson().fromJson(result, MovieListResponse::class.java)
        }
        return null
    }

    fun onBackPressed(){
        (activity as HomeActivity).finish()
    }

    companion object {
        val TAG = MovieFragment::class.java.simpleName
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        movieRvAdapter.filter.filter(s)
    }
}
