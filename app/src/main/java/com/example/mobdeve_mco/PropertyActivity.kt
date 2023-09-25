package com.example.mobdeve_mco

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
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

    private lateinit var tvDescription : TextView

    private lateinit var btnShowMore: Button
    private var isExpanded = false

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val location = LatLng(37.7749, -122.4194) // San Francisco coordinates
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in San Francisco"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)



        val property = intent.getParcelableExtra<Property>("property")
        if(property != null) {
            tvName = findViewById(R.id.tvName)
            imageSlider = findViewById(R.id.imageSlider)

            tvName.text = property.name

            val imageList = ArrayList<SlideModel>()

            for(image in property.imageList){
                imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
            }

            imageSlider.setImageList(imageList)
        }

        val window = this.window

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

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


        for (amenity in Amenity.values()) {
            val textView = getTextViewForAmenity(amenity)
            val amenityAvailable = property?.amenities?.get(amenity) ?: false
            if (!amenityAvailable) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        btnShowMore = findViewById(R.id.btnShowMore);
        btnShowMore.paintFlags = btnShowMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnShowMore.setOnClickListener {
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