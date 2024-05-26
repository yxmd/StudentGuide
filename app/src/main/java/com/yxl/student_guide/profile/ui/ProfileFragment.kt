package com.yxl.student_guide.profile.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.yxl.student_guide.R
import com.yxl.student_guide.utils.toScore
import com.yxl.student_guide.databinding.FragmentProfileBinding
import com.yxl.student_guide.profile.adapter.ScoreAdapter
import com.yxl.student_guide.profile.data.Score
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val scoreAdapter = ScoreAdapter()
    private lateinit var arrayAdapter: ArrayAdapter<String>

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
        viewModel.subjects.observe(viewLifecycleOwner) { array ->
            arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, array.map{
                it.name
            })
        }
    }

    private fun setupRecycler() {
        binding.rvScores.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scoreAdapter
        }

        viewModel.scores.observe(viewLifecycleOwner) {
            scoreAdapter.differ.submitList(it.map { score -> score.toScore() })
        }
        viewModel.totalScore.observe(viewLifecycleOwner){
            binding.tvAddScore.text = "Итоговый балл: $it"
        }
        scoreAdapter.setOnClickListener(object  : ScoreAdapter.OnClickListener{
            override fun onDeleteClick(position: Int, model: Score) {
                viewModel.deleteScore(model)
            }
        })

    }

    private fun openScoreDialog() {
        var selectedItem = ""

        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogLayout = inflater.inflate(R.layout.fragment_score, null)
            val scoreName =
                dialogLayout.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            scoreName.setAdapter(arrayAdapter)
            scoreName.setOnItemClickListener { parent, _, position, _ ->
                selectedItem = parent.getItemAtPosition(position) as String
            }
            val scoreValue = dialogLayout.findViewById<TextInputEditText>(R.id.etScoreValue)
            builder.setView(dialogLayout)

                .setPositiveButton("Добавить") { _, _ ->
                    if (selectedItem.isNotBlank() && scoreValue != null) {
                        viewModel.addScoreToDb(
                            selectedItem,
                            scoreValue.text.toString().toInt()
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.enter_all_lines), Toast.LENGTH_SHORT
                        ).show()
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