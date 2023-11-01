package com.example.mobdeve_mco

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
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

    private lateinit var sharedPreferences: SharedPreferences

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

    private var floorArea = -1
    private var floor = -1


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
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        bindViews(view)
        getSharedPreferences()
        init()
        setListeners()
        setViewModels()
    }

    private fun setViewModels(){
        val floorAreaViewModel = ViewModelProvider(requireActivity()).get(FloorAreaViewModel::class.java)

        floorAreaViewModel.floorArea.observe(viewLifecycleOwner, Observer { value ->
            tvFloorArea.text = value
            floorArea = value.toInt()
            with(sharedPreferences.edit()) {
                putInt("floorArea", floorArea)
                apply()
            }
        })

        val floorViewModel = ViewModelProvider(requireActivity()).get(FloorViewModel::class.java)

        floorViewModel.floor.observe(viewLifecycleOwner, Observer { floorValue ->
            tvFloor.text = appendFloorSuffix(floorValue)
            floor = floorValue.toInt()
            with(sharedPreferences.edit()) {
                putInt("floor", floor)
                apply()
            }
        })
    }

    private fun appendFloorSuffix(floorValue : String) : String{
        val floorWithSuffix = when {
            floorValue.endsWith("1") -> "$floorValue${"st"}"
            floorValue.endsWith("2") -> "$floorValue${"nd"}"
            floorValue.endsWith("3") -> "$floorValue${"rd"}"
            else -> "$floorValue${"th"}"
        }
        return floorWithSuffix
    }

    private fun getSharedPreferences(){
        numBedroom = sharedPreferences.getInt("numBedroom", 1)
        numBathroom = sharedPreferences.getInt("numBathroom", 1)
        isUserInputFloorArea = sharedPreferences.getBoolean("isUserInputFloorArea", false)
        isUserInputFloor = sharedPreferences.getBoolean("isUserInputFloor", false)
        cbIsFurnished.isChecked = sharedPreferences.getBoolean("isFurnished", false)
        cbBalcony.isChecked = sharedPreferences.getBoolean("withBalcony", false)
        cbIsStudioType.isChecked = sharedPreferences.getBoolean("isStudioType", false)
        floorArea = sharedPreferences.getInt("floorArea", 23)
        floor = sharedPreferences.getInt("floor", 23)
    }


    private fun bindViews(view: View) {
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

        tvNumBedroom.text = numBedroom.toString()
        tvNumBathroom.text = numBathroom.toString()
        tvFloorArea.text = floorArea.toString()
        tvFloor.text = appendFloorSuffix(floor.toString())


        if(isUserInputFloor){
            tvFloor.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
        }

        if(isUserInputFloorArea){
            tvFloorArea.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
        }

    }

    private fun setListeners(){
        btnAddBedroom.setOnClickListener {
            if (numBedroom < maxBedroom) {
                numBedroom++
                tvNumBedroom.text = numBedroom.toString()

                with(sharedPreferences.edit()) {
                    putInt("numBedroom", numBedroom)
                    apply()
                }
            }
        }

        btnMinusBedroom.setOnClickListener {
            if (numBedroom > minBedroom) {
                numBedroom--
                tvNumBedroom.text = numBedroom.toString()
            }

            with(sharedPreferences.edit()) {
                putInt("numBedroom", numBedroom)
                apply()
            }
        }

        btnAddBathroom.setOnClickListener {
            if (numBathroom < maxBathroom) {
                numBathroom++
                tvNumBathroom.text = numBathroom.toString()
            }

            with(sharedPreferences.edit()) {
                putInt("numBathroom", numBathroom)
                apply()
            }
        }

        btnMinusBathroom.setOnClickListener {
            if (numBathroom > minBathroom) {
                numBathroom--
                tvNumBathroom.text = numBathroom.toString()
            }

            with(sharedPreferences.edit()) {
                putInt("numBathroom", numBathroom)
                apply()
            }
        }

        cbIsFurnished.setOnCheckedChangeListener { _, isChecked ->
            cbIsFurnished.isChecked = isChecked
            with(sharedPreferences.edit()) {
                putBoolean("isFurnished", isChecked)
                apply()
            }
        }

        cbBalcony.setOnCheckedChangeListener { _, isChecked ->
            cbBalcony.isChecked = isChecked
            with(sharedPreferences.edit()) {
                putBoolean("withBalcony", isChecked)
                apply()
            }
        }

        cbIsStudioType.setOnCheckedChangeListener { _, isChecked ->
            cbIsStudioType.isChecked = isChecked
            with(sharedPreferences.edit()) {
                putBoolean("isStudioType", isChecked)
                apply()
            }
        }


        tvFloorArea.setOnClickListener {
            val bottomSheetFragment = GetFloorAreaFragment.newInstance(tvFloorArea.text.toString().toInt())
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            isUserInputFloorArea = true
            with(sharedPreferences.edit()) {
                putBoolean("isUserInputFloorArea", isUserInputFloorArea)
                apply()
            }
            updateButtons()

            val delayMillis = 300L
            val handler = Handler()
            handler.postDelayed({
                tvFloorArea.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
            }, delayMillis)
        }

        tvFloor.setOnClickListener {
            val bottomSheetFragment = GetFloorFragment.newInstance(extractNumericValue(tvFloor.text.toString()))
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            isUserInputFloor = true
            with(sharedPreferences.edit()) {
                putBoolean("isUserInputFloor", isUserInputFloor)
                apply()
            }
            updateButtons()

            val delayMillis = 300L
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

    override fun onResume() {
        super.onResume()
        updateButtons()
    }


    private fun updateButtons() {
        val activity = requireActivity() as AppCompatActivity
        val btnNext = activity.findViewById<Button>(R.id.btnNext)

        Log.d("test", "step3 next button")

        if(isUserInputFloor && isUserInputFloorArea){
            Log.d("test", "can go")

            btnNext.isEnabled = true
            btnNext.setTextColor(resources.getColor(android.R.color.white))
        } else {
            Log.d("test", "cannot go")

            btnNext.isEnabled = false
            btnNext.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}