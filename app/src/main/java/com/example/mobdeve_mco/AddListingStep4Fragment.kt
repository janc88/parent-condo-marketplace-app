package com.example.mobdeve_mco

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentAddListingStep4Binding

class AddListingStep4Fragment : Fragment() {
    private var _binding: FragmentAddListingStep4Binding? = null
    private val binding: FragmentAddListingStep4Binding get() = _binding!!

    private lateinit var btnAddPhotos: Button
    private lateinit var btnTakePhotos : Button
    private val selectedImages: ArrayList<Uri> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep4Binding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setListeners()
    }

    private fun bindViews(view: View) {
        btnAddPhotos = view.findViewById(R.id.btnAddPhotos)
        btnTakePhotos = view.findViewById(R.id.btnTakePhotos)
    }

    private fun setListeners() {
        btnAddPhotos.setOnClickListener {
            openGallery()
        }

        btnTakePhotos.setOnClickListener {
            // Handle taking photos here
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUris = data?.clipData
            if (selectedImageUris != null) {
                selectedImages.clear() // Clear the previous selection
                for (i in 0 until selectedImageUris.itemCount) {
                    val selectedImageUri = selectedImageUris.getItemAt(i).uri
                    selectedImages.add(selectedImageUri)
                }
                // Now, the selected image URIs are stored in the selectedImages list
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
