package com.example.mobdeve_mco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ListingActivity : AppCompatActivity() {

    private lateinit var ivListingImg : ImageView
    private lateinit var tvListingTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        val listing = intent.getParcelableExtra<Listing>("listing")
        if(listing != null) {
            ivListingImg = findViewById(R.id.ivListingImg)
            tvListingTitle = findViewById(R.id.tvListingTitle)
            ivListingImg.setImageResource(listing.image)
            tvListingTitle.text = listing.title
        }
    }
}