package com.yxl.student_guide.institute.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session.SearchListener
import com.yandex.runtime.image.ImageProvider
import com.yxl.student_guide.R
import com.yxl.student_guide.databinding.FragmentInstituteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstituteFragment : Fragment() {

    private lateinit var binding: FragmentInstituteBinding
    private val viewModel: InstituteViewModel by viewModels()
    private val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)
    private var isDescriptionExpanded = false
    private var collapsedHeight = 0
    private var halfHeight = 0
    private var expandedHeight = 0

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
            "university" -> {
                viewModel.getUniversityInfo(arguments?.getInt("id")!!)
                viewModel.institute.observe(viewLifecycleOwner) {
                    binding.apply {
                        ivPoster.load(it.img)
                        tvTitle.text = it.name
                        tvDescription.text = it.description
                        setupDescriptionToggle()
                    }
                }
            }

            "college" -> {
                viewModel.getCollegeInfo(arguments?.getInt("id")!!)
                viewModel.institute.observe(viewLifecycleOwner) {
                    binding.apply {
                        ivPoster.load(it.img)
                        tvTitle.text = it.name
                        tvDescription.text = it.description
                        setupDescriptionToggle()
                    }
                }
            }

            else -> {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        val searchOptions = SearchOptions().apply {
            searchTypes = SearchType.GEO.value
            resultPageSize = 32
        }

        val searchSessionListener = object : SearchListener {
            override fun onSearchResponse(response: Response) {
                val mapObjects: MapObjectCollection =
                    binding.mapView.mapWindow.map.mapObjects
                mapObjects.clear()
                for (searchResult in response.collection.children) {
                    val resultLocation = searchResult.obj!!.geometry[0].point
                    if (resultLocation != null) {
                        val cameraPosition = CameraPosition(resultLocation, 16.0f, 0.0f, 0.0f)
                        binding.mapView.mapWindow.map.move(cameraPosition)
                        mapObjects.addPlacemark { placemark: PlacemarkMapObject ->
                            placemark.geometry = resultLocation
                            placemark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.ic_pin))
                        }

                        return
                    }
                }
            }

            override fun onSearchError(p0: com.yandex.runtime.Error) {
                Toast.makeText(requireContext(), "Problem loading the map...", Toast.LENGTH_SHORT).show()
            }
        }

        val session = searchManager.submit(
            viewModel.institute.value?.city ?: "",
            VisibleRegionUtils.toPolygon(binding.mapView.mapWindow.map.visibleRegion),
            searchOptions,
            searchSessionListener,
        )
    }

    private fun setupDescriptionToggle() {
        binding.tvDescription.post {
            collapsedHeight = binding.tvDescription.height

            // Measure expanded height
            binding.tvDescription.maxLines = Integer.MAX_VALUE
            binding.tvDescription.measure(
                View.MeasureSpec.makeMeasureSpec(binding.tvDescription.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            expandedHeight = binding.tvDescription.measuredHeight

            // Measure half height
            val halfLineCount = (binding.tvDescription.lineCount / 2).coerceAtLeast(1)
            binding.tvDescription.maxLines = halfLineCount
            binding.tvDescription.measure(
                View.MeasureSpec.makeMeasureSpec(binding.tvDescription.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            halfHeight = binding.tvDescription.measuredHeight

            // Set initial state to half content
            binding.tvDescription.height = halfHeight

            binding.tvDescription.setOnClickListener {
                if (isDescriptionExpanded) {
                    collapseDescription()
                } else {
                    expandDescription()
                }
            }
        }
    }

    private fun expandDescription() {
        animateDescriptionHeight(binding.tvDescription.height, expandedHeight)
        binding.tvDescription.maxLines = Integer.MAX_VALUE
        isDescriptionExpanded = true
    }

    private fun collapseDescription() {
        animateDescriptionHeight(binding.tvDescription.height, halfHeight)
        binding.tvDescription.maxLines = Integer.MAX_VALUE
        isDescriptionExpanded = false
    }

    private fun animateDescriptionHeight(startHeight: Int, endHeight: Int) {
        val heightAnimator = ValueAnimator.ofInt(startHeight, endHeight)
        heightAnimator.addUpdateListener { animator ->
            val value = animator.animatedValue as Int
            binding.tvDescription.height = value
        }
        heightAnimator.interpolator = AccelerateDecelerateInterpolator()
        heightAnimator.duration = 300
        heightAnimator.start()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    private fun createRow(specialty: String, form: String, faculty: String): TableRow {
        val row =
            LayoutInflater.from(requireContext()).inflate(R.layout.item_table_row, null) as TableRow
        row.findViewById<TextView>(R.id.tvSpecialty).text = specialty
        row.findViewById<TextView>(R.id.tvForm).text = form
        row.findViewById<TextView>(R.id.tvFaculty).text = faculty

        return row
    }
}
