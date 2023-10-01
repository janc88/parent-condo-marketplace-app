package com.example.mobdeve_mco

import DummyData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingsActivity : AppCompatActivity() {

    private var listingIds: ArrayList<Int>? = null
    private lateinit var propertyName: String
    private lateinit var listings: ArrayList<Listing>
    private lateinit var rvSearchResults: RecyclerView
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var tvPropertyName: TextView
    private lateinit var btnBack: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings)

        listingIds = intent.getIntegerArrayListExtra("listingIds")
        propertyName = intent.getStringExtra("propertyName")!!

        listings = getListings()

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