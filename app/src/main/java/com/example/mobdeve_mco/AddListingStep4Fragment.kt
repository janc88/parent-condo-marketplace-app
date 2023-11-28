package com.example.mobdeve_mco

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mc.GridSpacingItemDecoration
import com.example.mobdeve_mco.databinding.FragmentAddListingStep4Binding


class AddListingStep4Fragment : Fragment(), OnAddMoreClickListener, ImageRemoveClickListener {

    private var _binding: FragmentAddListingStep4Binding? = null
    private val binding: FragmentAddListingStep4Binding get() = _binding!!

    private lateinit var btnAddPhotos: Button
    private lateinit var btnTakePhotos: Button
    private lateinit var llTakePhotos : LinearLayout
    private lateinit var llAddPhotos : LinearLayout
    private val selectedImages: MutableList<ImageItem> = mutableListOf()
    private lateinit var rvImages: RecyclerView

    private lateinit var preferencesManager : ImagePreferencesManager
    private lateinit var imageAdapter : ImageAdapter

    private var photosUploaded = false



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onImageRemoveClick(position: Int) {
        val imageToRemove = selectedImages[position].imageUri
        imageAdapter.removeImage(position)
        updateButtonVisibility()

        preferencesManager.removeImage(imageToRemove)
    }

    override fun onAddMoreClick() {
        openGallery()
        updateButtonVisibility()
    }

    override fun onResume() {
        super.onResume()
        updateButtons()
    }

    private fun updateButtons() {
        val activity = requireActivity() as AppCompatActivity
        val btnNext = activity.findViewById<Button>(R.id.btnNext)

        if (photosUploaded) {
            btnNext.isEnabled = true
            btnNext.setTextColor(resources.getColor(android.R.color.white))
        } else {
            btnNext.isEnabled = false
            btnNext.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }

    private fun updateButtonVisibility() {
        if (selectedImages.isNotEmpty()) {
            btnAddPhotos.visibility = View.GONE
            btnTakePhotos.visibility = View.GONE
            llTakePhotos.visibility = View.GONE
            llAddPhotos.visibility = View.GONE

            photosUploaded = true
        } else {
            btnAddPhotos.visibility = View.VISIBLE
            btnTakePhotos.visibility = View.VISIBLE
            llTakePhotos.visibility = View.VISIBLE
            llAddPhotos.visibility = View.VISIBLE

            photosUploaded = false
        }

        updateButtons()
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)
        init()
        setListeners()

        val storedImageURIs = preferencesManager.getImages()

        selectedImages.clear()
        selectedImages.addAll(storedImageURIs.map { ImageItem(it) })

        imageAdapter.submitList(selectedImages)
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
        preferencesManager = ImagePreferencesManager(requireContext())
        imageAdapter = ImageAdapter(this, this, selectedImages, preferencesManager)

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
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                val newSelectedImages: MutableList<ImageItem> = mutableListOf()

                for (i in 0 until selectedImageUris.itemCount) {
                    val selectedImageUri = selectedImageUris.getItemAt(i).uri
                    newSelectedImages.add(ImageItem(selectedImageUri))

//                    val contentResolver = requireContext().contentResolver
//                    contentResolver.takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                    preferencesManager.saveImage(selectedImageUri)
                }


                selectedImages.clear()
                selectedImages.addAll(newSelectedImages)
                imageAdapter.submitList(selectedImages)
                updateButtonVisibility()
            }
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
