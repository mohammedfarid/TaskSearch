package com.swvl.tasksearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.swvl.tasksearch.R
import com.swvl.tasksearch.helper.PaginationScrollListenerGrid
import com.swvl.tasksearch.model.movieresponse.MoviesItem
import com.swvl.tasksearch.model.photoresponse.PhotoItem
import com.swvl.tasksearch.model.photoresponse.PhotoListResponse
import com.swvl.tasksearch.retrofit.home.Status
import com.swvl.tasksearch.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : BaseFragment() {

    val homeViewModel: HomeViewModel by viewModel()

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var imageRvAdapter: ImageRvAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1
    var nextValue: Boolean = false
    lateinit var photoListResponse: PhotoListResponse

    var moviesItem: MoviesItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchBundle(arguments)
    }

    private fun fetchBundle(arguments: Bundle?) {
        if (arguments != null) {
            moviesItem = arguments.getParcelable(MOVIES_ITEM_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(R.layout.fragment_detail, contentContainer, true)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        layoutManager = GridLayoutManager(mContext, 2)
        rv_movie_image.layoutManager = layoutManager
        callImageApi()
        rv_movie_image.addOnScrollListener(object :
            PaginationScrollListenerGrid(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                if (nextValue) {
                    callImageApi()
                } else {
                    isLastPage = true
                }
            }
        })
    }

    private fun callImageApi() {
        moviesItem?.title?.let {
            homeViewModel.getMovieList(it, page.toString()).observe(viewLifecycleOwner,
                Observer { result ->
                    result.let {
                        when (result.status) {
                            Status.LOADING -> {
                                playLoadingAnimation()
                                isLoading = true
                            }
                            Status.ERROR -> {
                                pauseLoadingAnimation()
                                fillData()
                                isLoading = false
                            }
                            Status.SUCCESS -> {
                                pauseLoadingAnimation()
                                isLoading = false
                                base_content.visibility = View.VISIBLE
                                fillData()
                                result.data.let { response ->
                                    response?.body()?.let {
                                        photoListResponse = it
                                    }

                                    if (page == 1) {
                                        imageRvAdapter = ImageRvAdapter(
                                            mContext,
                                            photoListResponse.photos?.photo as ArrayList<PhotoItem?>?
                                        )
                                        rv_movie_image.adapter = imageRvAdapter
                                    } else {
                                        imageRvAdapter.addData(photoListResponse.photos?.photo as ArrayList<PhotoItem>?)
                                    }
                                    photoListResponse.photos?.pages?.let { page_number ->
                                        if (page < page_number) {
                                            page += 1
                                            nextValue = true
                                        } else {
                                            nextValue = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun fillData() {
        movieTitleBody.text = moviesItem?.title
        movieYearBody.text = moviesItem?.year.toString()
        if (moviesItem?.genres.isNullOrEmpty()) {
            llMovieGenres.visibility = View.GONE
        } else {
            llMovieGenres.visibility = View.VISIBLE
            movieGenresBody.adapter = moviesItem?.genres?.let { genres ->
                ArrayAdapter<String>(
                    mContext, R.layout.item_movie_list, R.id.text1,
                    genres
                )
            }
        }
        if (moviesItem?.cast.isNullOrEmpty()) {
            llMovieCasts.visibility = View.GONE
        } else {
            llMovieCasts.visibility = View.VISIBLE
            movieCastsBody.adapter = moviesItem?.cast?.let { casts ->
                ArrayAdapter<String>(
                    mContext, R.layout.item_movie_list, R.id.text1,
                    casts
                )
            }
        }
    }

    fun onBackPressed() {
        (activity as HomeActivity).supportFragmentManager.popBackStack()
    }

    companion object {
        val TAG = DetailFragment::class.java.simpleName
        const val MOVIES_ITEM_KEY = "movies_item_key"
        fun newInstant(moviesItem: MoviesItem) = run {
            val fragment = DetailFragment()
            fragment.arguments = bundleOf(
                MOVIES_ITEM_KEY to moviesItem
            )
            return@run fragment
        }
    }
}
