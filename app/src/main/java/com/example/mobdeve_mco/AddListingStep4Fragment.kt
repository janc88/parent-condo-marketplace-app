package com.example.mobdeve_mco

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mc.GridSpacingItemDecoration
import com.example.mobdeve_mco.databinding.FragmentAddListingStep4Binding
import java.io.File
import java.io.FileOutputStream


class AddListingStep4Fragment : Fragment(), OnAddMoreClickListener, ImageRemoveClickListener {

    private var _binding: FragmentAddListingStep4Binding? = null
    private val binding: FragmentAddListingStep4Binding get() = _binding!!

    private lateinit var btnAddPhotos: Button
    private lateinit var btnTakePhotos: Button
    private lateinit var llTakePhotos : LinearLayout
    private lateinit var llAddPhotos : LinearLayout
    private val selectedImages: MutableList<ImageItem> = mutableListOf()
    private lateinit var rvImages: RecyclerView
    private val imageAdapter = ImageAdapter(this, this, selectedImages)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onImageRemoveClick(position: Int) {
        imageAdapter.removeImage(position)
        updateButtonVisibility()
    }
    override fun onAddMoreClick() {
        openGallery()
        updateButtonVisibility()
    }

    private fun updateButtonVisibility() {
        if (selectedImages.isNotEmpty()) {
            btnAddPhotos.visibility = View.GONE
            btnTakePhotos.visibility = View.GONE
            llTakePhotos.visibility = View.GONE
            llAddPhotos.visibility = View.GONE
        } else {
            btnAddPhotos.visibility = View.VISIBLE
            btnTakePhotos.visibility = View.VISIBLE
            llTakePhotos.visibility = View.VISIBLE
            llAddPhotos.visibility = View.VISIBLE
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)
        init()
        setListeners()
        val storedImages = retrieveImagesFromStorage()
        if (storedImages.isNotEmpty()) {
            selectedImages.addAll(storedImages.map { ImageItem(it) })
            imageAdapter.submitList(selectedImages)
        }
        updateButtonVisibility()


    }

    private fun bindViews(view: View) {
        btnAddPhotos = binding.btnAddPhotos
        btnTakePhotos = binding.btnTakePhotos
        rvImages = binding.rvImages
        llAddPhotos = binding.llAddPhotos
        llTakePhotos = binding.llTakePhotos
    }

    private fun init() {
        rvImages.layoutManager = GridLayoutManager(requireContext(), 2)
        rvImages.addItemDecoration(GridSpacingItemDecoration(2, 20))
        rvImages.adapter = imageAdapter
        rvImages.itemAnimator = null
    }

    private fun setListeners() {
        btnAddPhotos.setOnClickListener {
            openGallery()
        }

        btnTakePhotos.setOnClickListener {
            // not implemented
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
                    selectedImages.add(ImageItem(selectedImageUri))
                }

                // Now, the selected image URIs are stored in the selectedImages list

                // Update the RecyclerView with the selected images
                imageAdapter.submitList(selectedImages)
                saveImagesToStorage(selectedImages)
                updateButtonVisibility()
            }
        }
    }

    private fun saveImagesToStorage(selectedImages: List<ImageItem>) {
        for ((index, imageItem) in selectedImages.withIndex()) {
            val imageUri = imageItem.imageUri
            val inputStream = requireContext().contentResolver.openInputStream(imageUri)
            val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_$index.jpg")
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            inputStream?.close()
            outputStream.close()
        }
    }

    private fun retrieveImagesFromStorage(): List<Uri> {
        val imageUris: MutableList<Uri> = mutableListOf()
        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        if (directory != null && directory.exists()) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    val imageUri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.mobdeve_mco",
                        file
                    )
                    imageUris.add(imageUri)
                }
            }
        }

        return imageUris
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
