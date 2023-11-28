package com.example.mobdeve_mco

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class EditProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var etBio : TextInputEditText
    private lateinit var etContactNum : TextInputEditText
    private lateinit var btnClose : ImageButton
    private lateinit var btnDone : Button
    private lateinit var ivPfp : ShapeableImageView

    private val GALLERY_REQUEST_CODE = 1001

    private val firebaseHelper = FirebaseHelper.getInstance()


    private var imageUri: Uri? = null
    private var onProfileUpdatedListener: OnProfileUpdatedListener? = null

    interface OnProfileUpdatedListener {
        fun onProfileUpdated(success: Boolean)
    }

    fun setOnProfileUpdatedListener(listener: OnProfileUpdatedListener) {
        onProfileUpdatedListener = listener
    }


    override fun getTheme(): Int {
        return R.style.LoginBottomSheetDialogTheme
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            imageUri = data?.data
            ivPfp.setImageURI(imageUri)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etBio = view.findViewById(R.id.etBio)
        etContactNum = view.findViewById(R.id.etContactNum)
        btnClose = view.findViewById(R.id.btnClose)
        btnDone = view.findViewById(R.id.btnDone)
        ivPfp = view.findViewById(R.id.ivPfp)

        val user = firebaseHelper.getCurrentUser()
        firebaseHelper.getUserFromFirestore(user!!.uid){user ->
            if(user != null){
                if(user.pfp != "") {
                    Picasso.get().load(user.pfp).into(ivPfp)
                }
                etBio.setText(user.bio.toString())
                etContactNum.setText(user.contactNum)
            }
        }

        ivPfp.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }

        btnDone.setOnClickListener {
            val bio = etBio.text.toString()
            val contactNum = etContactNum.text.toString()
            firebaseHelper.updateProfile(bio, contactNum, imageUri) { success ->
                onProfileUpdatedListener?.onProfileUpdated(success)
                dismiss()
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
                val behaviour = BottomSheetBehavior.from(it)
                //setupFullHeight(it)
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

