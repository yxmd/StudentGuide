package com.yxl.student_guide.main.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.databinding.FragmentContentBinding
import com.yxl.student_guide.institute.ui.InstituteFragment
import com.yxl.student_guide.main.MainActivity
import com.yxl.student_guide.main.adapters.ContentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentFragment : Fragment() {

    private lateinit var binding: FragmentContentBinding
    private val viewModel by activityViewModels<ContentViewModel>()
    private val adapter = ContentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        setUpTabLayout()
        setUpSearching()

        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
            binding.rvContent.isVisible = !it
        }
        binding.bFilter.setOnClickListener {
            BottomSheetFilterFragment().show(parentFragmentManager, "BottomSheetFilterFragment")
        }
        viewModel.showErrorButton.observe(viewLifecycleOwner){
            if(it == true){
                //viewModel manage error type
                binding.bRetry.visibility = View.VISIBLE
            }
        }
        binding.bRetry.setOnClickListener {
            viewModel.getData()
            binding.bRetry.visibility = View.GONE
            viewModel.showErrorButton.postValue(false)
        }
    }

    private fun setUpTabLayout() {
        binding.tabLayout.getTabAt(0)?.select()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {

                    viewModel.tab.value = it
                    viewModel.getData()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // no need
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // no need
            }
        })
    }

    private fun setUpRecycler() {
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
        }

        adapter.setOnClickListener(object : ContentAdapter.OnClickListener{
            override fun onLongClick(position: Int, model: Institute) {
                openAddToFavDialog(model)
            }

            override fun onInstituteClick(position: Int, model: Institute) {
                (activity as MainActivity).addFragment(InstituteFragment(), bundleOf("type" to "uni", "id" to model.id))
            }
        })
    }

    private fun setUpSearching(){
        binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    if(it.isNotBlank()){
                        viewModel.searchInstitutes(query)
                    }else{
                        viewModel.getData()
                    }
                }

                return false
            }
        })
    }

    private fun openAddToFavDialog(institute: Institute) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Добавить в избранное")
        builder.setMessage("Вы действительно хотите добавить ${institute.name} в избранное?")
        builder.setPositiveButton("Да") { _, _ ->
            viewModel.addInstituteToDb(institute)
        }
        builder.setNegativeButton("Отменить") { _, _ ->

        }
        val dialog = builder.create()
        dialog.show()
    }

}