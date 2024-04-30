package com.yxl.student_guide.profile.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.student_guide.R
import com.yxl.student_guide.databinding.FragmentProfileBinding
import com.yxl.student_guide.profile.adapter.ScoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val scoreAdapter = ScoreAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        binding.fabAddScore.setOnClickListener { openScoreDialog() }
    }

    private fun setupRecycler(){
        binding.rvScores.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scoreAdapter
        }

        viewModel.scores.observe(viewLifecycleOwner){
            scoreAdapter.differ.submitList(it)
            if(it.isNotEmpty()){
                binding.tvAddScore.text = "Ваши баллы"
            }
        }

    }

    private fun openScoreDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogLayout = inflater.inflate(R.layout.fragment_score, null)
            val scoreName = dialogLayout.findViewById<EditText>(R.id.etScoreName)
            val scoreValue = dialogLayout.findViewById<EditText>(R.id.etScoreValue)
            builder.setView(dialogLayout)

                .setPositiveButton("Добавить") { _, _ ->
                    if(scoreName != null && scoreValue != null){
                        viewModel.addScoreToDb(
                            scoreName.text.toString(),
                            scoreValue.text.toString().toInt()
                        )
                    }
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
            builder.show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}