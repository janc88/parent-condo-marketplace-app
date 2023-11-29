package com.example.mobdeve_mco

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.UUID


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
        val propertyId = sharedPreferences.getString("propertyId", "")
        val propertyName = sharedPreferences.getString("propertyName", "")
        val area = sharedPreferences.getInt("floorArea", -1)
        val floor = sharedPreferences.getInt("floor", -1)
        val numBedroom = sharedPreferences.getInt("numBedroom", -1)
        val numBathroom = sharedPreferences.getInt("numBathroom", -1)
        val isFurnished = sharedPreferences.getBoolean("isFurnished", false)
        val isStudioType = sharedPreferences.getBoolean("isStudioType", false)
        val balcony = sharedPreferences.getBoolean("withBalcony", false)

        val imagePreferencesManager = ImagePreferencesManager(this)
        val imageUris: List<Uri> = imagePreferencesManager.getImages()

        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val ownerUid = currentUser.uid // This is the UID of the current logged-in user
            val newListing = Listing(
                imageList = ArrayList(),
                title = title,
                price = price.toInt(),
                property = propertyName.toString(),
                propertyId = propertyId.toString(),
                university = university,
                area = area.toDouble(),
                furnished = isFurnished,
                studioType = isStudioType,
                numBedroom = numBedroom,
                numBathroom = numBathroom,
                floor = floor,
                balcony = balcony,
                ownerId = ownerUid,
                description = description,
                rented = false
            )

            val imageUploadTasks = uploadImagesToFirebaseStorage(imageUris)

            Tasks.whenAllComplete(imageUploadTasks)
                .addOnSuccessListener { _ ->
                    getImageDownloadURLs(imageUploadTasks)
                        .addOnSuccessListener { downloadUrls ->
                            val imageUrls = downloadUrls.map { it.toString() }
                            newListing.imageList = ArrayList(imageUrls)

                            val db = FirebaseFirestore.getInstance()
                            val listingsRef = db.collection("listings")

                            listingsRef.add(newListing)
                                .addOnSuccessListener { documentReference ->
                                    val documentId = documentReference.id
                                    val updatedData = mapOf("id" to documentId)

                                    // Update the Firestore document with the new data
                                    listingsRef.document(documentId)
                                        .update(updatedData)
                                        .addOnSuccessListener {
                                            // Handle success if needed
                                        }
                                        .addOnFailureListener { e ->
                                            // Handle failure if needed

                                        }

                                    // Update the properties collection
                                    val propertiesRef = db.collection("properties")
                                    val query = propertiesRef.whereEqualTo("id", propertyId)

                                    query.get()
                                        .addOnSuccessListener { querySnapshot ->
                                            val batch = db.batch()
                                            for (document in querySnapshot.documents) {
                                                // Update the listingIds field with the new listingId
                                                val listingIds = document.get("listingIds") as MutableList<String>? ?: mutableListOf()
                                                listingIds.add(documentId)
                                                batch.update(document.reference, "listingIds", listingIds)

                                                // Increment the numListings field by 1
                                                val currentNumListings = document.getLong("numListings") ?: 0
                                                batch.update(document.reference, "numListings", currentNumListings + 1)

                                                val currentHighestPrice = document.getLong("highestPrice") ?: 0
                                                val currentLowestPrice = document.getLong("lowestPrice") ?: 0

                                                val newListingPrice = newListing.price.toLong()

                                                if (newListingPrice > currentHighestPrice) {
                                                    batch.update(document.reference, "highestPrice", newListingPrice)
                                                }

                                                if (newListingPrice < currentLowestPrice || currentLowestPrice.toInt() == 0) {
                                                    batch.update(document.reference, "lowestPrice", newListingPrice)
                                                }
                                            }

                                            // Commit the batch update
                                            batch.commit()
                                                .addOnSuccessListener {
                                                    clearSharedPreferences()

                                                    Toast.makeText(this, "Listing created successfully", Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener { e ->
                                                    // Handle the batch update failure
                                                    Toast.makeText(this, "Error updating property references: ${e.message}", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                        .addOnFailureListener { e ->
                                            // Handle the Firestore query failure
                                            Toast.makeText(this, "Error querying properties: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                                .addOnFailureListener { e ->
                                    // Handle the Firestore write failure
                                    Toast.makeText(this, "Error creating listing: ${e.message}", Toast.LENGTH_SHORT).show()
                                }

                        }
                        .addOnFailureListener { e ->
                            // Handle the image URL retrieval failure
                            Toast.makeText(this, "Error retrieving image URLs: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    // Handle the image upload failure
                    Toast.makeText(this, "Error uploading images: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Handle the case where no user is logged in
        }


    }

    private fun uploadImagesToFirebaseStorage(imageUris: List<Uri>): List<Task<UploadTask.TaskSnapshot>> {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageUploadTasks = ArrayList<Task<UploadTask.TaskSnapshot>>()

        for ((index, uri) in imageUris.withIndex()) {
            val id = UUID.randomUUID().toString()
            val imageRef = storageRef.child("images/$id.jpg")
            val uploadTask = imageRef.putFile(uri)

            imageUploadTasks.add(uploadTask)
        }

        return imageUploadTasks
    }

    private fun getImageDownloadURLs(imageUploadTasks: List<Task<UploadTask.TaskSnapshot>>): Task<List<Uri>> {
        val tasks = imageUploadTasks.map { task ->
            task.continueWithTask { uploadTask ->
                if (!uploadTask.isSuccessful) {
                    throw uploadTask.exception!!
                }
                return@continueWithTask uploadTask.result?.storage?.downloadUrl
            }
        }

        return Tasks.whenAllSuccess<Uri>(tasks)
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
