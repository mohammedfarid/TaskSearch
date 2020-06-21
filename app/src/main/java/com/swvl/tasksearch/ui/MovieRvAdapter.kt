package com.swvl.tasksearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.swvl.tasksearch.R
import com.swvl.tasksearch.model.movieresponse.MoviesItem

class MovieRvAdapter(
    var context: Context,
    var moviesItemArrayList: List<MoviesItem?>?,
    var filteredList: List<MoviesItem?>?,
    var onClickItem: (MoviesItem) -> Unit
) :
    RecyclerView.Adapter<MovieRvAdapter.MyHolder>(), Filterable {

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: AppCompatTextView = itemView.findViewById(R.id.movieTitleBody)
        val movieYear: AppCompatTextView = itemView.findViewById(R.id.movieYearBody)
        val movieRating: AppCompatTextView = itemView.findViewById(R.id.movieRatingBody)
        fun bind(moviesItem: MoviesItem) {
            movieTitle.text = moviesItem.title
            movieYear.text = moviesItem.year.toString()
            movieRating.text = moviesItem.rating.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesItemArrayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        moviesItemArrayList?.get(position)?.let { moviesItem -> holder.bind(moviesItem) }

        holder.itemView.setOnClickListener {
            moviesItemArrayList?.get(position)?.let { moviesItem -> onClickItem(moviesItem) }
        }
    }

    override fun getFilter() = filterable
    private val filterable = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val charString = constraint.toString()

            moviesItemArrayList = if (charString.isEmpty()) {
                filteredList
            } else {
                val filteredListTemp = ArrayList<MoviesItem?>()
                for (row in filteredList!!) {
                    if (row?.year.toString().contains(charString)) {
                         filteredListTemp.add(row)
                    }
                }
                filteredListTemp
            }

            val filterResults = FilterResults()
            filterResults.values = moviesItemArrayList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            moviesItemArrayList = results!!.values as List<MoviesItem?>?
            notifyDataSetChanged()
        }
    }
}
