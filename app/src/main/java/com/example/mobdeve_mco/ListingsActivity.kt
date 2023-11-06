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
import com.google.firebase.firestore.FirebaseFirestore

class ListingsActivity : AppCompatActivity() {

    private var listingIds: ArrayList<String> = ArrayList()
    private lateinit var propertyName: String
    private var listings: ArrayList<Listing> = ArrayList()
    private lateinit var rvSearchResults: RecyclerView
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var tvPropertyName: TextView
    private lateinit var btnBack: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings)

        listingIds = intent.getStringArrayExtra("listingIds")?.toCollection(ArrayList()) ?: ArrayList()
        propertyName = intent.getStringExtra("propertyName")!!


        getListingsFromFirestore(listingIds!!)


        tvPropertyName = findViewById(R.id.tvPropertyName)
        tvPropertyName.text = propertyName

        rvSearchResults= findViewById(R.id.rvSearchResults)

        rvSearchResults.setHasFixedSize(true)
        rvSearchResults.layoutManager = LinearLayoutManager(this)

        listingAdapter = ListingAdapter(listings)
        rvSearchResults.adapter = listingAdapter

        listingAdapter.onItemClick = {
            val intent = Intent(this, ListingActivity::class.java)
            intent.putExtra("listing", it)
            startActivity(intent)
        }

        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            onBackPressed()
        }

    }

    private fun getListingsFromFirestore(uids: ArrayList<String>) {
        val db = FirebaseFirestore.getInstance()
        val listingsRef = db.collection("listings")

        val listings = mutableListOf<Listing>()

        for (uid in uids) {
            val documentRef = listingsRef.document(uid)

            documentRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val listing = documentSnapshot.toObject(Listing::class.java)
                        if (listing != null) {
                            listings.add(listing)
                            listingAdapter.notifyDataSetChanged()
                        }
                    }
                    Log.d("gettinglistings", "success in getting listings")
                    Log.d("gettinglistings", listings[0].title)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error fetching listing with UID $uid: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.d("gettinglistings", "failure in getting listings")
                }
        }
    }



//    private fun getListings(): ArrayList<Listing> {
//        val result = ArrayList<Listing>()
//        listingIds?.let { nonNullListingIds ->
//            for (listing in DummyData.listingList) {
//                if (listing.id in nonNullListingIds) {
//                    result.add(listing)
//                }
//            }
//        }
//        return result
//    }


}