package com.example.mobdeve_mco

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.FirebaseFirestore


class AddListingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button
    private lateinit var addListingFormAdapter: AddListingFormAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing)

        bindViews()
        init()
        setListeners()
        isAnyDataSaved()
    }

    private fun bindViews(){
        viewPager = findViewById(R.id.viewPager)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun init(){
        addListingFormAdapter = AddListingFormAdapter(this)
        viewPager.adapter = addListingFormAdapter

        viewPager.isUserInputEnabled = false
        btnBack.paintFlags = btnBack.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val progressDrawable = progressBar.progressDrawable?.mutate()
        progressDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_IN)
        progressBar.progressDrawable = progressDrawable
    }

    private fun setListeners(){
        btnBack.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            } else{
                // user clicks back on the first page
                showConfirmationDialog()
            }
            updateProgressBar()
        }

        btnNext.setOnClickListener {
            if (viewPager.currentItem < addListingFormAdapter.itemCount - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            } else {
                // user clicks next on the last page
                saveListingToFirestore()
            }
            updateProgressBar()
        }

    }

    private fun saveListingToFirestore(){
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val title = sharedPreferences.getString("title", "") ?: ""
        val description = sharedPreferences.getString("description", "") ?: ""
        val price = sharedPreferences.getString("price", "") ?: ""
        val university = mapNumberToUniversity(sharedPreferences.getInt("university", -1))
        val propertyId = sharedPreferences.getInt("property", -1)
        val area = sharedPreferences.getInt("floorArea", -1)
        val floor = sharedPreferences.getInt("floor", -1)
        val numBedroom = sharedPreferences.getInt("numBedroom", -1)
        val numBathroom = sharedPreferences.getInt("numBathroom", -1)
        val isFurnished = sharedPreferences.getBoolean("isFurnished", false)
        val isStudioType = sharedPreferences.getBoolean("isStudioType", false)
        val balcony = sharedPreferences.getBoolean("withBalcony", false)

        val imagePreferencesManager = ImagePreferencesManager(this)
        val imageUris: List<Uri> = imagePreferencesManager.getImages()

        val newListing = Listing(
            id = 0,
            imageList = ArrayList(),
            title = title,
            price = price.toInt(),
            property = "",
            propertyId = propertyId,
            university = university,
            area = area.toDouble(),
            isFurnished = isFurnished,
            isStudioType = isStudioType,
            numBedroom = numBedroom,
            numBathroom = numBathroom,
            floor = floor,
            balcony = balcony,
            ownerId = 0,
            description = description,
            isRented = false
        )

        val db = FirebaseFirestore.getInstance()
        val listingsRef = db.collection("listings")

        listingsRef.add(newListing)
            .addOnSuccessListener { documentReference ->
                val documentId = documentReference.id
                Toast.makeText(this, "Listing created successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Error occurred while writing the document
                // Handle the failure case, such as showing an error message to the user.
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }



    }

    private fun isAnyDataSaved() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("university")) {
            showSavedDialog()
        }
    }

    private fun showSavedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your previous progress has been saved.")

        builder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            // Handle "OK" button click
            // You can add any action you want here
        }

        val dialog = builder.create()
        dialog.show()
    }



    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to go back? Your changes will not be saved.")


        builder.setNegativeButton("Go Back") { dialog: DialogInterface, which: Int ->
            clearSharedPreferences()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        builder.setPositiveButton("Continue Editing") { dialog: DialogInterface, which: Int ->

        }

        val dialog = builder.create()
        dialog.show()

        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negativeButton.setTextColor(Color.BLACK)

    }



    private fun updateProgressBar(){
        val progress = (viewPager.currentItem * 100) / (addListingFormAdapter.itemCount - 1)
        progressBar.progress = progress
    }

    override fun onBackPressed() {
        if (viewPager.currentItem > 0) {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        } else {
            // user clicks back on the first page
            showConfirmationDialog()
        }
    }

    private fun clearSharedPreferences() {
        val myPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val imagePrefs = getSharedPreferences("image_prefs", Context.MODE_PRIVATE)

        val myPrefsEditor = myPrefs.edit()
        myPrefsEditor.clear()
        myPrefsEditor.apply()

        val imagePrefsEditor = imagePrefs.edit()
        imagePrefsEditor.clear()
        imagePrefsEditor.apply()
    }

}
