package com.yxl.student_guide.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.yxl.student_guide.databinding.FragmentContentBinding
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
            viewModel.getData(0)
            binding.bRetry.visibility = View.GONE
            viewModel.showErrorButton.postValue(false)
        }
    }

    private fun setUpTabLayout() {
        binding.tabLayout.getTabAt(0)?.select()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    Log.d("contentFragment", "getData")
                    viewModel.getData(it)
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
    }

}