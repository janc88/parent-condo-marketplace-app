package com.example.mobdeve_mco

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.NumberPicker
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GetFloorFragment : BottomSheetDialogFragment() {

    private lateinit var numberPicker: NumberPicker
    private lateinit var btnClose: ImageButton
    private lateinit var btnDone: Button

    private val maxFloor = 50
    private val minFloor = 1

    companion object {
        private const val ARG_INITIAL_VALUE = "initialValue"

        fun newInstance(initialValue: Int): GetFloorFragment {
            val fragment = GetFloorFragment()
            val args = Bundle()
            args.putInt(ARG_INITIAL_VALUE, initialValue)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_floor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnClose = view.findViewById(R.id.btnClose)
        btnDone = view.findViewById(R.id.btnDone)
        numberPicker = view.findViewById(R.id.numberPicker)

        val floorViewModel = ViewModelProvider(requireActivity()).get(FloorViewModel::class.java)

        btnClose.setOnClickListener {
            val enteredValue = numberPicker.value.toString()
            floorViewModel.setFloor(enteredValue)
            dismiss()
        }

        btnDone.setOnClickListener {
            val enteredValue = numberPicker.value.toString()
            floorViewModel.setFloor(enteredValue)
            dismiss()
        }

        val floorLabels = generateFloorLabels(minFloor, maxFloor)
        numberPicker.minValue = minFloor
        numberPicker.maxValue = maxFloor
        numberPicker.displayedValues = floorLabels

        val initialValue = arguments?.getInt(ARG_INITIAL_VALUE) ?: minFloor
        numberPicker.value = initialValue
    }





    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.isDraggable = false
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun generateFloorLabels(min: Int, max: Int): Array<String> {
        val labels = mutableListOf<String>()
        for (i in min..max) {
            val label = when {
                i % 10 == 1 && i % 100 != 11 -> "${i}st"
                i % 10 == 2 && i % 100 != 12 -> "${i}nd"
                i % 10 == 3 && i % 100 != 13 -> "${i}rd"
                else -> "${i}th"
            }
            labels.add(label)
        }
        return labels.toTypedArray()
    }

}

