package com.yxl.student_guide.institute.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.yxl.student_guide.R
import com.yxl.student_guide.databinding.FragmentInstituteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstituteFragment : Fragment() {

    private lateinit var binding: FragmentInstituteBinding
    private val viewModel: InstituteViewModel by viewModels()

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
        when (arguments?.getString("type") ?: "null") {
            "uni" -> {
                viewModel.getUniversityInfo(arguments?.getInt("id")!!)
                viewModel.university.observe(viewLifecycleOwner) {
                    binding.apply {
                        ivPoster.load(it.img)
                        tvTitle.text = it.name
                        tvDescription.text = it.description
                        table.addView(createRow("Автоматизация технологических процессов и производств", "дневная", "Электросвязи"))
                        table.addView(createRow("Системы сети инфокоммуникаций", "дневная", "Электросвязи"))
                        table.addView(createRow("Автоматизация технологических процессов и производств", "дневная", "Инжиниринга и технологий связи"))
                        table.addView(createRow("Автоматизация технологических процессов и производств", "дневная", "Электросвязи"))
                        table.addView(createRow("Автоматизация технологических процессов и производств", "дневная", "Электросвязи"))
                    }
                }
            }

            "cl" -> {
                viewModel.getCollegeInfo(arguments?.getInt("id")!!)
            }

            else -> {
                Toast.makeText(requireContext(), "something gone wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createRow(specialty: String, form: String, faculty: String) : TableRow{
        val row =
            LayoutInflater.from(requireContext()).inflate(R.layout.item_table_row, null) as TableRow
        row.findViewById<TextView>(R.id.tvSpecialty).text = specialty
        row.findViewById<TextView>(R.id.tvForm).text = form
        row.findViewById<TextView>(R.id.tvFaculty).text = faculty

        return row
    }


}