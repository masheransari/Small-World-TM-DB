package com.example.smallworld_tm_db.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smallworld_tm_db.application.AppConstants
import com.example.smallworld_tm_db.core.BaseViewHolder
import com.example.smallworld_tm_db.databinding.MovieItem2Binding
import com.example.smallworld_tm_db.domain.model.MovieUiModel

class MoviesAdapter(
    val movieDetailsAction: (movieUi: MovieUiModel, itemBinding: MovieItem2Binding) -> Unit,
    var searchQuery: String = ""
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mItemsMovie = listOf<MovieUiModel>() // Original movie list
    var filteredMoviesList = listOf<MovieUiModel>() // Filtered movie list

    // Initialize both lists with the same data
    init {
        mItemsMovie = emptyList()
        filteredMoviesList = emptyList()
    }

//    var mItemsMovie = listOf<MovieUiModel>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UpcomingMoviesViewHolder(itemBinding, parent.context, searchQuery)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            movieDetailsAction(filteredMoviesList[position], itemBinding)
        }

        return holder
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingMoviesViewHolder -> holder.bind(filteredMoviesList[position])
        }
    }

    override fun getItemCount(): Int = filteredMoviesList.size

    private inner class UpcomingMoviesViewHolder(
        val binding: MovieItem2Binding,
        val context: Context,
        var searchQuery: String
    ) : BaseViewHolder<MovieUiModel>(binding.root) {
        override fun bind(item: MovieUiModel) {

            binding.apply {
                tvTitle.text = item.title
                Glide.with(context).load("${AppConstants.IMAGE_URL}${item.posterPath}")
                    .centerCrop().into(imvImagePoster)
            }
            ViewCompat.setTransitionName(binding.imvImagePoster, "avatar_${item.id}")
            ViewCompat.setTransitionName(binding.tvTitle, "title_${item.id}")
        }
    }

    fun submitList(movieList: List<MovieUiModel>) {
        mItemsMovie = movieList // Store the original movie list
        filterMovies() // Apply the filter
    }

    fun filterMovies() {
        filteredMoviesList = if (searchQuery.isBlank()) {
            mItemsMovie // If searchQuery is empty, show all items
        } else {
            // Filter the original list based on searchQuery
            mItemsMovie.filter { item ->
                item.title.contains(searchQuery, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}