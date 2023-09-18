package com.example.smallworld_tm_db.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smallworld_tm_db.R
import com.example.smallworld_tm_db.core.Resource
import com.example.smallworld_tm_db.databinding.FragmentMovieBinding
import com.example.smallworld_tm_db.databinding.MovieItem2Binding
import com.example.smallworld_tm_db.domain.model.MovieUiModel
import com.example.smallworld_tm_db.ui.main.adapters.MoviesAdapter
import com.example.smallworld_tm_db.ui.presentation.MovieViewModel
import com.example.smallworld_tm_db.ui.utils.hide
import com.example.smallworld_tm_db.ui.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private val etSearch: EditText by lazy {
        binding.root.findViewById<EditText>(R.id.etSearch)
    }

    private val mAdapterMoviesList by lazy {
        MoviesAdapter(
            { movie, binding ->
                goToMovieDetailsView(movie, binding)
            },
            etSearch.text.toString()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mAdapterMoviesList
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                mAdapterMoviesList.searchQuery = s.toString()
                mAdapterMoviesList.filterMovies()
            }
        })
        observePopularMoviesList()
    }

    private fun observePopularMoviesList() {
        viewModel.movieStateResource.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    mAdapterMoviesList.submitList(result.data.results)
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

    private fun goToMovieDetailsView(movieSelected: MovieUiModel, binding: MovieItem2Binding) {
        val extras = FragmentNavigatorExtras(
            binding.imvImagePoster to "avatar_${movieSelected.id}",
            binding.tvTitle to "title_${movieSelected.id}",
        )
        etSearch.setText("")
        findNavController().navigate(
            MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movieSelected.id),
            extras
        )
    }
}