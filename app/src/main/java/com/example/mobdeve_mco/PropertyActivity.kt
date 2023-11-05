package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class PropertyActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private lateinit var tvName : TextView
    private lateinit var imageSlider : ImageSlider

    private lateinit var tvGym : TextView
    private lateinit var tvElevator : TextView
    private lateinit var tvFireAlarm : TextView
    private lateinit var tvWaterTank : TextView
    private lateinit var tvParking  : TextView
    private lateinit var tvCCTV : TextView
    private lateinit var tvMailroom : TextView
    private lateinit var tvGenerator : TextView
    private lateinit var tvSecurity : TextView
    private lateinit var tvPool : TextView
    private lateinit var tvWifi : TextView
    private lateinit var tvPriceRange : TextView
    private lateinit var tvNumListings : TextView
    private lateinit var tvAddress : TextView
    private lateinit var tvNoFound: TextView
    private lateinit var tvDescription : TextView

    private lateinit var btnShowMore: Button
    private lateinit var btnSeeAll: Button
    private lateinit var btnSeeListings: Button
    private lateinit var property: Property

    private lateinit var btnBack: CardView
    private lateinit var cvHeart: CardView
    private lateinit var btnHeart: ImageView

    private var longitude: Double? = null
    private var latitude: Double? = null

    private lateinit var rvFeaturedListings: RecyclerView
    private lateinit var featuredListingAdapter: FeaturedListingAdapter

    private lateinit var featuredListings: ArrayList<Listing>

    private var isLiked = false
    private var isExpanded = false

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val zoomLevel = 16.0f
        val location = LatLng(latitude!!, longitude!!)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
        googleMap.addMarker(MarkerOptions().position(location).title(""))
        googleMap.animateCamera(cameraUpdate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        property = intent.getParcelableExtra<Property>("property")!!

        bindViews()
        init()

        rvFeaturedListings = findViewById(R.id.rvFeaturedListings)

        rvFeaturedListings.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFeaturedListings.layoutManager = layoutManager

        featuredListings = getRandomListings(5, property.listingIds)

        featuredListingAdapter = FeaturedListingAdapter(featuredListings)
        rvFeaturedListings.adapter = featuredListingAdapter

        featuredListingAdapter.onItemClick = {
            val intent = Intent(this, ListingActivity::class.java)
            intent.putExtra("listing", it)
            startActivity(intent)
        }

        if (property.listingIds.isNullOrEmpty()) {
            tvNoFound.isVisible = true
            rvFeaturedListings.isVisible = false
            btnSeeAll.isVisible = false
        } else {
            tvNoFound.isVisible = false
            rvFeaturedListings.isVisible = true
        }



        val window = this.window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        btnShowMore.setOnClickListener {
            handleShowMore()
        }

        btnSeeListings.setOnClickListener{
            seeListings()
        }

        btnSeeAll.setOnClickListener{
            seeListings()
        }


    }




    private fun bindViews(){
        tvName = findViewById(R.id.tvName)
        imageSlider = findViewById(R.id.imageSlider)
        tvGym = findViewById(R.id.tvGym)
        tvElevator = findViewById(R.id.tvElevator)
        tvFireAlarm = findViewById(R.id.tvFireAlarm)
        tvWaterTank = findViewById(R.id.tvWaterTank)
        tvParking = findViewById(R.id.tvParking)
        tvCCTV = findViewById(R.id.tvCCTV)
        tvMailroom = findViewById(R.id.tvMailroom)
        tvGenerator = findViewById(R.id.tvGenerator)
        tvSecurity = findViewById(R.id.tvSecurity)
        tvPool = findViewById(R.id.tvPool)
        tvWifi = findViewById(R.id.tvWifi)
        tvDescription = findViewById(R.id.tvDescription)
        btnShowMore = findViewById(R.id.btnShowMore)
        btnSeeAll = findViewById(R.id.btnSeeAll)
        btnSeeListings = findViewById(R.id.btnSeeListings)
        tvPriceRange = findViewById(R.id.tvPrice)
        tvNumListings = findViewById(R.id.tvNumListings)
        tvAddress = findViewById(R.id.tvListingTitle)
        tvNoFound = findViewById(R.id.tvNoFound)
        btnBack = findViewById(R.id.btnBack)
        cvHeart = findViewById(R.id.cvHeart)
        btnHeart = findViewById(R.id.btnHeart)
    }

    private fun init(){
        btnBack.setOnClickListener{
            onBackPressed()
        }

        cvHeart.setOnClickListener{
            if (isLiked) {
                btnHeart.setImageResource(R.drawable.ic_heart)
            } else {
                btnHeart.setImageResource(R.drawable.ic_heart_liked)
            }
            isLiked = !isLiked
        }


        val imageList = ArrayList<SlideModel>()
        for(image in property.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }

        for (amenity in Amenity.values()) {
            val textView = getTextViewForAmenity(amenity)
            val amenityAvailable = when (amenity) {
                Amenity.SWIMMING_POOL -> property.SWIMMING_POOL
                Amenity.GYM -> property.GYM
                Amenity.PARKING -> property.PARKING
                Amenity.WIFI -> property.WIFI
                Amenity.ELEVATORS -> property.ELEVATORS
                Amenity.FIRE_ALARM -> property.FIRE_ALARM
                Amenity.SECURITY -> property.SECURITY
                Amenity.GENERATOR -> property.GENERATOR
                Amenity.CCTV -> property.CCTV
                Amenity.WATER_TANK -> property.WATER_TANK
                Amenity.MAILROOM -> property.MAILROOM
            }
            if (!amenityAvailable) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }


        tvName.text = property.name
        imageSlider.setImageList(imageList)
        tvDescription.text = property.description
        longitude = property.longitude
        latitude = property.latitude
        btnShowMore.paintFlags = btnShowMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        btnSeeAll.paintFlags = btnSeeAll.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        tvPriceRange.text = "${property.lowestPrice.formatPrice()} - ${property.highestPrice.formatPrice()}"
        tvNumListings.text = "${property.numListings.toString()} Listings available"
        tvAddress.text = property.address
    }

    private fun Int.formatPrice(): String {
        val formatter = java.text.DecimalFormat("#,###")
        return "₱" + formatter.format(this.toLong())
    }

    private fun handleShowMore(){
        isExpanded = !isExpanded

        if (isExpanded) {
            tvDescription.maxLines = Integer.MAX_VALUE
            tvDescription.ellipsize = null
            btnShowMore.text = "Show Less"
        } else {
            tvDescription.maxLines = 3
            tvDescription.ellipsize = TextUtils.TruncateAt.END
            btnShowMore.text = "Show More"
        }
    }

    private fun seeListings(){
        val intent = Intent(this, ListingsActivity::class.java)
        if(property != null){
            intent.putExtra("listingIds", property.listingIds)
            intent.putExtra("propertyName", property.name)
        }
        startActivity(intent)
    }



    private fun getTextViewForAmenity(amenity: Amenity): TextView {
        return when (amenity) {
            Amenity.SWIMMING_POOL -> tvPool
            Amenity.GYM -> tvGym
            Amenity.PARKING -> tvParking
            Amenity.WIFI -> tvWifi
            Amenity.ELEVATORS -> tvElevator
            Amenity.FIRE_ALARM -> tvFireAlarm
            Amenity.SECURITY -> tvSecurity
            Amenity.GENERATOR -> tvGenerator
            Amenity.CCTV -> tvCCTV
            Amenity.WATER_TANK -> tvWaterTank
            Amenity.MAILROOM -> tvMailroom
        }
    }
}

