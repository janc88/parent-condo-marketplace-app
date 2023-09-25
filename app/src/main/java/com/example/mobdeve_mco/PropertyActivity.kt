package com.example.mobdeve_mco

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


class PropertyActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

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


        for (amenity in Amenity.values()) {
            val textView = getTextViewForAmenity(amenity)
            val amenityAvailable = property?.amenities?.get(amenity) ?: false
            if (!amenityAvailable) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
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