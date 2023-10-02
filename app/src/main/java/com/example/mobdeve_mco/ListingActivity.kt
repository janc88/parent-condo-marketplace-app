package com.example.mobdeve_mco

import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


class ListingActivity : AppCompatActivity() {

    private lateinit var tvListingTitle : TextView
    private lateinit var imageSlider : ImageSlider

    private lateinit var tvPropertyNameTop : TextView
    private lateinit var tvSqm : TextView
    private lateinit var tvFurnished : TextView
    private lateinit var tvBedroom : TextView
    private lateinit var tvFloor : TextView
    private lateinit var tvBathroom : TextView
    private lateinit var tvBalcony : TextView

    private lateinit var tvOwner : TextView
    private lateinit var tvOwnerJoined : TextView
    private lateinit var tvPropertyNameBottom : TextView
    private lateinit var tvAddress : TextView

    private lateinit var rvSimilarListings: RecyclerView

    private lateinit var listing: Listing


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        listing = intent.getParcelableExtra<Listing>("listing")!!

        bindViews()
        init()

        val window = this.window

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

    }

    private fun bindViews(){
        tvListingTitle = findViewById(R.id.tvListingTitle)
        imageSlider = findViewById(R.id.imageSlider)
        tvPropertyNameTop = findViewById(R.id.tvPropertyNameTop)
        tvSqm = findViewById(R.id.tvSqm)
        tvFurnished = findViewById(R.id.tvFurnished)
        tvBedroom = findViewById(R.id.tvBedroom)
        tvFloor = findViewById(R.id.tvFloor)
        tvBathroom = findViewById(R.id.tvBathroom)
        tvBalcony = findViewById(R.id.tvBalcony)
        tvOwner = findViewById(R.id.tvOwner)
        tvOwnerJoined = findViewById(R.id.tvOwnerJoined)
        tvPropertyNameBottom = findViewById(R.id.tvPropertyNameBottom)
        tvAddress = findViewById(R.id.tvAddress)
        rvSimilarListings = findViewById(R.id.rvSimilarListings)
    }

    private fun init(){
        tvListingTitle.text = listing.title

        val imageList = ArrayList<SlideModel>()
        for(image in listing.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }
        imageSlider.setImageList(imageList)

        tvPropertyNameTop.text = listing.property

    }
}