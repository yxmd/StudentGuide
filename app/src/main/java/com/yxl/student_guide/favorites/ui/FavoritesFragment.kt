package com.yxl.student_guide.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.student_guide.databinding.FragmentFavoritesBinding
import com.yxl.student_guide.favorites.adapters.FavoritesAdapter
import com.yxl.student_guide.utils.toInstitute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesAdapter = FavoritesAdapter()
    private val viewModel: FavoritesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.rvFavorites.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.institutes.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                binding.tvNothingHere.visibility = View.GONE
            }
            favoritesAdapter.differ.submitList(it.map { item -> item.toInstitute() })
        }
    }

}
