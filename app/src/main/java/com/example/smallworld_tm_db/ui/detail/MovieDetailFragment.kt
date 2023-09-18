package com.example.smallworld_tm_db.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.smallworld_tm_db.R
import com.example.smallworld_tm_db.application.AppConstants
import com.example.smallworld_tm_db.core.Resource
import com.example.smallworld_tm_db.databinding.FragmentMovieDetailBinding
import com.example.smallworld_tm_db.domain.model.MovieUiModel
import com.example.smallworld_tm_db.ui.presentation.MovieDetailViewModel
import com.example.smallworld_tm_db.ui.utils.hide
import com.example.smallworld_tm_db.ui.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    private lateinit var movieArg: MovieUiModel
    private var movieID: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        movieID = MovieDetailFragmentArgs.fromBundle(args).movieId
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        observePopularMoviesList()
        viewModel.fetchMovie(movieID.toString())
    }

    private fun updateMovieInfo(){
        ViewCompat.setTransitionName(binding.imvImagePoster, "avatar_${movieArg.id}")
        ViewCompat.setTransitionName(binding.tvTitle, "title_${movieArg.id}")
        ViewCompat.setTransitionName(binding.txtDescription, "description_${movieArg.id}")

        Glide.with(requireContext()).load("${AppConstants.IMAGE_URL}${movieArg.posterPath}")
            .centerCrop().into(binding.imvImagePoster)
        Glide.with(requireContext()).load("${AppConstants.IMAGE_URL}${movieArg.backdropImageUrl}")
            .centerCrop().into(binding.imgBackground)
        binding.txtDescription.text = movieArg.overview
        binding.tvTitle.text = movieArg.title
        binding.tvLanguage.text = "Language ${movieArg.originalLanguage}"
        binding.tvRating.text = "${movieArg.voteAverage} (${movieArg.voteCount} Reviews)"
        binding.tvReleased.text = "Released ${movieArg.releaseDate}"

    }

    private fun observePopularMoviesList() {
        viewModel.movieStateResource.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    movieArg = result.data
                    updateMovieInfo()
                }

                is Resource.Failure -> {
                    binding.progressBar.show()
                    Log.e("FetchError", "Error: ${result.exception} ")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

}