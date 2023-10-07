package com.example.mobdeve_mco

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.NumberPicker
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GetFloorAreaFragment : BottomSheetDialogFragment() {

    private lateinit var numberPicker: NumberPicker
    private lateinit var btnClose: ImageButton

    private val maxFloorArea = 100
    private val minFloorArea = 10

    companion object {
        private const val ARG_INITIAL_VALUE = "initialValue"

        fun newInstance(initialValue: Int): GetFloorAreaFragment {
            val fragment = GetFloorAreaFragment()
            val args = Bundle()
            args.putInt(ARG_INITIAL_VALUE, initialValue)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_floor_area, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnClose = view.findViewById(R.id.btnClose)
        numberPicker = view.findViewById(R.id.numberPicker)

        numberPicker.minValue = minFloorArea
        numberPicker.maxValue = maxFloorArea


        val floorAreaViewModel = ViewModelProvider(requireActivity()).get(FloorAreaViewModel::class.java)

        btnClose.setOnClickListener {
            val enteredValue = numberPicker.value.toString()
            floorAreaViewModel.setFloorArea(enteredValue)
            dismiss()
        }

        val initialValue = arguments?.getInt(ARG_INITIAL_VALUE) ?: minFloorArea
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


}

