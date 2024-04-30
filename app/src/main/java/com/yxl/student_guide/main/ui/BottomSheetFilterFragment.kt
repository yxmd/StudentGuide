package com.yxl.student_guide.main.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yxl.student_guide.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding
    private val viewModel: ContentViewModel by viewModels()

    private val programs = listOf("Информационные технологии", "Медицина", "Филология")
    private val cities = listOf("Минск", "Брест", "Витебск", "Гомель", "Гродно")

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

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbCity.id -> {
                    binding.sCities.visibility = View.VISIBLE
                    setupSpinner(binding.sCities, cities)
                }
                binding.rbProgram.id -> {
                    binding.sPrograms.visibility = View.VISIBLE
                    setupSpinner(binding.sPrograms, programs)
                }
            }
        }

        binding.bApply.setOnClickListener {

            dismiss()
        }

        binding.bCancel.setOnClickListener {
            dismiss()
        }
    }


    private fun observeRadioStates() {

    }

    private fun setupSpinner(spinner: Spinner, items: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

}