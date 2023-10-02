package com.example.mobdeve_mco

import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        val window = this.window

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

    }
}