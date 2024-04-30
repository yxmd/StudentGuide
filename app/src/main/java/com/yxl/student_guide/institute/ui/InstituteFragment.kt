package com.yxl.student_guide.institute.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yxl.student_guide.databinding.FragmentInstituteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstituteFragment: Fragment() {

    private lateinit var binding: FragmentInstituteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstituteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}