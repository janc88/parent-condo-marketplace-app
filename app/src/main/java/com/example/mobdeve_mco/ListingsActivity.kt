package com.example.mobdeve_mco

import DummyData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ListingsActivity : AppCompatActivity() {

    private var listingIds: ArrayList<String> = ArrayList()
    private lateinit var propertyName: String
    private lateinit var propertyId: String


    private var listings = ArrayList<Listing>()

    private lateinit var rvSearchResults: RecyclerView
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var tvPropertyName: TextView
    private lateinit var btnBack: CardView

    private val firebaseHelper = FirebaseHelper.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings)


        propertyName = intent.getStringExtra("propertyName")!!
        propertyId = intent.getStringExtra("propertyId")!!


        firebaseHelper.getAvailableListingsForProperty(propertyId){listings ->
            rvSearchResults.setHasFixedSize(true)
            rvSearchResults.layoutManager = LinearLayoutManager(this)

            listingAdapter = ListingAdapter(ArrayList(listings), supportFragmentManager)
            rvSearchResults.adapter = listingAdapter

            listingAdapter.onItemClick = {
                val intent = Intent(this, ListingActivity::class.java)
                intent.putExtra("listing", it)
                startActivity(intent)
            }
        }


        tvPropertyName = findViewById(R.id.tvPropertyName)
        tvPropertyName.text = propertyName

        rvSearchResults= findViewById(R.id.rvSearchResults)


        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            onBackPressed()
        }

    }

//    private fun getListingsFromFirestore(propertyId: String, onListingsReceived: (List<Listing>) -> Unit) {
//        val db = Firebase.firestore
//        val listingsRef = db.collection("listings")
//
//        listingsRef
//            .whereEqualTo("propertyId", propertyId)
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                val matchingListings = mutableListOf<Listing>()
//
//                for (document in querySnapshot.documents) {
//                    val listingData = document.toObject(Listing::class.java)
//                    if (listingData != null) {
//                        matchingListings.add(listingData)
//                    }
//                }
//
//                onListingsReceived(matchingListings)
//            }
//            .addOnFailureListener { e ->
//                onListingsReceived(emptyList())
//            }
//    }



}