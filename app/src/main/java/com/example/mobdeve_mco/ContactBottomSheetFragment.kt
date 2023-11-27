package com.example.mobdeve_mco

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContactBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var tvEmail : TextView
    private lateinit var tvContactNum : TextView
    private lateinit var btnClose : ImageButton
    private lateinit var tvContactNumLabel : TextView

    companion object {
        private const val ARG_EMAIL = "email"
        private const val ARG_CONTACT_NUM = "contact_num"

        fun newInstance(email: String, contactNum: String): ContactBottomSheetFragment {
            val fragment = ContactBottomSheetFragment()
            val args = Bundle()
            args.putString(ARG_EMAIL, email)
            args.putString(ARG_CONTACT_NUM, contactNum)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getTheme(): Int {
        return R.style.LoginBottomSheetDialogTheme
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmail = view.findViewById(R.id.tvEmail)
        tvContactNum = view.findViewById(R.id.tvContactNum)
        tvContactNumLabel = view.findViewById(R.id.tvContactNumLabel)
        btnClose = view.findViewById(R.id.btnClose)

        val email = arguments?.getString(ARG_EMAIL)
        val contactNum = arguments?.getString(ARG_CONTACT_NUM)

        tvEmail.text = email

        if (contactNum != null) {
            if(contactNum.isNotEmpty()) {
                tvContactNum.text = contactNum
            } else{
//                tvContactNum.isVisible = false
//                tvContactNumLabel.isVisible = false
                tvContactNum.text = "Not available"
            }
        }

        btnClose.setOnClickListener{
            dismiss()
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

}

