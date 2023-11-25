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
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var btnLogIn : Button
    private lateinit var btnSignUp : Button
    private lateinit var btnClose : ImageButton
    private lateinit var btnGoogle : AppCompatButton


    override fun getTheme(): Int {
        return R.style.LoginBottomSheetDialogTheme
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogIn = view.findViewById(R.id.btnLogIn)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        btnGoogle = view.findViewById(R.id.btnGoogle)
        btnClose = view.findViewById(R.id.btnClose)

        btnLogIn.setOnClickListener{

        }

        btnSignUp.setOnClickListener{

        }

        btnGoogle.setOnClickListener{

        }

        btnClose.setOnClickListener{

        }
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
                //behaviour.isDraggable = false
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

