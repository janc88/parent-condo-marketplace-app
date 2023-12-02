package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
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
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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

    private var longitude: Double? = null
    private var latitude: Double? = null

    private lateinit var rvFeaturedListings: RecyclerView
    private lateinit var featuredListingAdapter: FeaturedListingAdapter

    private val firebaseHelper = FirebaseHelper.getInstance()

    private var isExpanded = false

    private val maxListingsToShow = 5


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

        rvFeaturedListings.isNestedScrollingEnabled = false;
        rvFeaturedListings.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFeaturedListings.layoutManager = layoutManager

        firebaseHelper.getAvailableListingsForProperty(property.id){listings ->
            if (listings != null) {
                val shuffledListings = listings.shuffled()
                val selectedListings = shuffledListings.take(maxListingsToShow)

                featuredListingAdapter = FeaturedListingAdapter(ArrayList(selectedListings))
                featuredListingAdapter.setHasStableIds(true)
                rvFeaturedListings.adapter = featuredListingAdapter

                featuredListingAdapter.onItemClick = {
                    val intent = Intent(this, ListingActivity::class.java)
                    intent.putExtra("listing", it)
                    startActivity(intent)
                }
            }
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


        if(property.numListings == 0){
            btnSeeListings.isEnabled = false
            btnSeeAll.isEnabled = false
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
    }

    private fun init(){
        btnBack.setOnClickListener{
            onBackPressed()
        }


        val imageList = ArrayList<SlideModel>()
        for(image in property.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }

        for (amenity in Amenity.values()) {
            val textView = getTextViewForAmenity(amenity)
            val amenityAvailable = when (amenity) {
                Amenity.SWIMMING_POOL -> property.swimmingPool
                Amenity.GYM -> property.gym
                Amenity.PARKING -> property.parking
                Amenity.WIFI -> property.wifi
                Amenity.ELEVATORS -> property.elevators
                Amenity.FIRE_ALARM -> property.fireAlarm
                Amenity.SECURITY -> property.security
                Amenity.GENERATOR -> property.generator
                Amenity.CCTV -> property.cctv
                Amenity.WATER_TANK -> property.waterTank
                Amenity.MAILROOM -> property.mailRoom
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
        if(property.numListings == 1){
            tvNumListings.text = "${property.numListings.toString()} Listing available"
        }else{
            tvNumListings.text = "${property.numListings.toString()} Listings available"
        }
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

        if (property != null) {
            intent.putExtra("propertyName", property.name)
            intent.putExtra("propertyId", property.id)
            startActivity(intent)
        }

    }

//    private fun getRandomListingsFromFirestore(propertyId: String, num: Int, onListingsReceived: (List<Listing>) -> Unit) {
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
//                matchingListings.shuffle()
//
//                val randomListings = matchingListings.take(num)
//
//                onListingsReceived(randomListings)
//            }
//            .addOnFailureListener { e ->
//                onListingsReceived(emptyList())
//            }
//    }



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

