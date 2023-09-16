package com.example.mobdeve_mco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.widget.ImageView
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class ListingActivity : AppCompatActivity() {

    private lateinit var tvListingTitle : TextView
    private lateinit var imageSlider : ImageSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        val listing = intent.getParcelableExtra<Listing>("listing")
        if(listing != null) {
            tvListingTitle = findViewById(R.id.tvListingTitle)
            imageSlider = findViewById(R.id.imageSlider)

            tvListingTitle.text = listing.title

            val imageList = ArrayList<SlideModel>()

            for(image in listing.imageList){
                imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
            }

            imageSlider.setImageList(imageList)
        }
    }
}