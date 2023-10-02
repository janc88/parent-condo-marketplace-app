package com.example.mobdeve_mco

import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var tvDescription : TextView

    private lateinit var tvOwner : TextView
    private lateinit var tvDateJoined : TextView
    private lateinit var tvPropertyNameBottom : TextView
    private lateinit var tvAddress : TextView

    private lateinit var tvStudioType : TextView

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
        tvDateJoined = findViewById(R.id.tvDateJoined)
        tvPropertyNameBottom = findViewById(R.id.tvPropertyNameBottom)
        tvAddress = findViewById(R.id.tvAddress)
        rvSimilarListings = findViewById(R.id.rvSimilarListings)
        tvStudioType = findViewById(R.id.tvStudioType)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun init(){
        tvListingTitle.text = listing.title

        val imageList = ArrayList<SlideModel>()
        for(image in listing.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }
        imageSlider.setImageList(imageList)

        tvPropertyNameTop.text = listing.property
        tvListingTitle.text = listing.title
        tvStudioType.isVisible = listing.isStudioType
        tvSqm.text = "${listing.area} sqm"

        if(listing.isFurnished){
            tvFurnished.text = "Furnished"
        }else{
            tvFurnished.text = "Unfurnished"
        }

        if(listing.numBedroom > 1){
            tvBedroom.text = "${listing.numBedroom} Bedrooms"
        }else{
            tvBedroom.text = "1 Bedroom"
        }

        tvFloor.text = formatFloor(listing.floor)

        if(listing.numBathroom > 1){
            tvBathroom.text = "${listing.numBathroom} Bathrooms"
        }else{
            tvBathroom.text = "1 Bathroom"
        }

        if(listing.balcony){
            tvBalcony.text = "Balcony"
        }else{
            tvBalcony.text = "No Balcony"
        }

        tvOwner.text = "Temp owner"
        tvDateJoined.text = "Joined Temp 2022"
        tvDescription.text = listing.description

    }

    fun formatFloor(floor: Int): String {
        return when {
            floor % 10 == 1 && floor % 100 != 11 -> "${floor}st floor"
            floor % 10 == 2 && floor % 100 != 12 -> "${floor}nd floor"
            floor % 10 == 3 && floor % 100 != 13 -> "${floor}rd floor"
            else -> "${floor}th floor"
        }
    }
}