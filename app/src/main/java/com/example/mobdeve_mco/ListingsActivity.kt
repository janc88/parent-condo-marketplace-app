package com.example.mobdeve_mco

import DummyData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingsActivity : AppCompatActivity() {

    private var listingIds: ArrayList<Int>? = null
    private lateinit var propertyName: String
    private lateinit var listings: ArrayList<Listing>
    private lateinit var rvSearchResults: RecyclerView
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var svExplore: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings)

        listingIds = intent.getIntegerArrayListExtra("listingIds")
        propertyName = intent.getStringExtra("propertyName")!!

        listings = getListings()

        rvSearchResults= findViewById(R.id.rvSearchResults)
        svExplore = findViewById(R.id.svExplore)

        rvSearchResults.setHasFixedSize(true)
        rvSearchResults.layoutManager = LinearLayoutManager(this)

        listingAdapter = ListingAdapter(listings)
        rvSearchResults.adapter = listingAdapter

        listingAdapter.onItemClick = {
            val intent = Intent(this, ListingActivity::class.java)
            intent.putExtra("listing", it)
            startActivity(intent)
        }





    }

    private fun getListings(): ArrayList<Listing>{
        val result = ArrayList<Listing>()
        listingIds?.let { nonNullListingIds ->
            for (listing in DummyData.listingList) {
                if (listing.id in nonNullListingIds) {
                    result.add(listing)
                }
            }
        }
        return result
    }

}