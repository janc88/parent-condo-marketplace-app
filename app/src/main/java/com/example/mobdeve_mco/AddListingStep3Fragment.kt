package com.example.mobdeve_mco

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobdeve_mco.databinding.FragmentAddListingStep3Binding

class AddListingStep3Fragment : Fragment() {
    private var _binding: FragmentAddListingStep3Binding? = null
    private val binding: FragmentAddListingStep3Binding get() = _binding!!

    private lateinit var btnAddBedroom : ImageButton
    private lateinit var btnAddBathroom : ImageButton
    private lateinit var btnMinusBedroom : ImageButton
    private lateinit var btnMinusBathroom : ImageButton

    private lateinit var tvNumBedroom : TextView
    private lateinit var tvNumBathroom : TextView

    private lateinit var tvFloorArea : TextView
    private lateinit var tvFloor : TextView

    private lateinit var cbIsFurnished : CheckBox
    private lateinit var cbBalcony : CheckBox
    private lateinit var cbIsStudioType : CheckBox

    private var isUserInputFloorArea = false
    private var isUserInputFloor = false

    private var numBedroom = 1
    private var numBathroom = 1

    private val maxBathroom = 5
    private val minBathroom = 1

    private val maxBedroom = 5
    private val minBedroom = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep3Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        init()
        setListeners()

        val floorAreaViewModel = ViewModelProvider(requireActivity()).get(FloorAreaViewModel::class.java)

        floorAreaViewModel.floorArea.observe(viewLifecycleOwner, Observer { value ->
            tvFloorArea.text = value
        })

        val floorViewModel = ViewModelProvider(requireActivity()).get(FloorViewModel::class.java)

        floorViewModel.floor.observe(viewLifecycleOwner, Observer { floorValue ->
            val floorWithSuffix = when {
                floorValue.endsWith("1") -> "$floorValue${"st"}"
                floorValue.endsWith("2") -> "$floorValue${"nd"}"
                floorValue.endsWith("3") -> "$floorValue${"rd"}"
                else -> "$floorValue${"th"}"
            }
            tvFloor.text = floorWithSuffix
        })


    }


    private fun bindViews(view: View){
        btnAddBedroom = view.findViewById(R.id.btnAddBedroom)
        btnAddBathroom = view.findViewById(R.id.btnAddBathroom)
        btnMinusBedroom = view.findViewById(R.id.btnMinusBedroom)
        btnMinusBathroom = view.findViewById(R.id.btnMinusBathroom)

        tvNumBedroom = view.findViewById(R.id.tvNumBedroom)
        tvNumBathroom = view.findViewById(R.id.tvNumBathroom)

        tvFloorArea = view.findViewById(R.id.tvFloorArea)
        tvFloor = view.findViewById(R.id.tvFloor)

        cbIsFurnished = view.findViewById(R.id.cbIsFurnished)
        cbBalcony = view.findViewById(R.id.cbBalcony)
        cbIsStudioType = view.findViewById(R.id.cbIsStudioType)

    }

    private fun init(){
        tvFloorArea.underlineText()
        tvFloor.underlineText()

    }

    private fun setListeners(){
        btnAddBedroom.setOnClickListener {
            if (numBedroom < maxBedroom) {
                numBedroom++
                tvNumBedroom.text = numBedroom.toString()
            }
        }

        btnMinusBedroom.setOnClickListener {
            if (numBedroom > minBedroom) {
                numBedroom--
                tvNumBedroom.text = numBedroom.toString()
            }
        }

        btnAddBathroom.setOnClickListener {
            if (numBathroom < maxBathroom) {
                numBathroom++
                tvNumBathroom.text = numBathroom.toString()
            }
        }

        btnMinusBathroom.setOnClickListener {
            if (numBathroom > minBathroom) {
                numBathroom--
                tvNumBathroom.text = numBathroom.toString()
            }
        }
        tvFloorArea.setOnClickListener {
            val bottomSheetFragment = GetFloorAreaFragment.newInstance(tvFloorArea.text.toString().toInt())
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            isUserInputFloorArea = true

            val delayMillis = 1000L
            val handler = Handler()
            handler.postDelayed({
                tvFloorArea.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
            }, delayMillis)
        }

        tvFloor.setOnClickListener {
            val bottomSheetFragment = GetFloorFragment.newInstance(extractNumericValue(tvFloor.text.toString()))
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            isUserInputFloor = true

            val delayMillis = 1000L
            val handler = Handler()
            handler.postDelayed({
                tvFloor.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
            }, delayMillis)
        }

    }

    private fun extractNumericValue(floorText: String): Int {
        val numericValueString = floorText.filter { it.isDigit() }
        return numericValueString.toIntOrNull() ?: 0
    }

    private fun accessButtons() {
        val activity = requireActivity() as AppCompatActivity
        val btnBack = activity.findViewById<Button>(R.id.btnBack)
        val btnNext = activity.findViewById<Button>(R.id.btnNext)

        btnBack.setOnClickListener {
            // Handle the Back button click
        }

        btnNext.setOnClickListener {
            // Handle the Next button click
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}