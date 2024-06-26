package com.yxl.student_guide.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yxl.student_guide.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding
    private val viewModel: ContentViewModel by activityViewModels()

    private val cities = listOf("Минск", "Брест", "Витебск", "Гомель", "Гродно", "Могилев")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRadioStates()
        var score = 0
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbCity.id -> {
                    binding.sCities.visibility = View.VISIBLE
                    setupSpinner(binding.sCities, cities)
                }
            }
        }

        viewModel.score.observe(viewLifecycleOwner){
            score = it
        }

        binding.bApply.setOnClickListener {
            when {
                binding.rbCity.isChecked -> {
                    viewModel.spinnerCitiesState.value = binding.sCities.selectedItemPosition
                    viewModel.filterByName(binding.sCities.selectedItem.toString())
                }
                binding.rbScore.isChecked -> {
                    viewModel.rbScoreState.value = true
                    viewModel.filterByScore(score)
                }
            }
            dismiss()
        }


        binding.bCancel.setOnClickListener {
            viewModel.spinnerCitiesState.value = null
            viewModel.rbScoreState.value = false
            viewModel.getData()
            dismiss()
        }
    }


    private fun observeRadioStates() {

        viewModel.spinnerCitiesState.observe(viewLifecycleOwner){
            if(it != null){
                binding.rbCity.isChecked = true
                binding.sCities.setSelection(it)
            }
            binding.bCancel.isEnabled = it != null
        }
        viewModel.rbScoreState.observe(viewLifecycleOwner){
            if(it != null){
                binding.rbScore.isChecked = it
            }
            binding.bCancel.isEnabled = it != null
        }
    }

    private fun setupSpinner(spinner: Spinner, items: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

}