package com.example.mobdeve_mco

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


class PropertyActivity : AppCompatActivity() {

    private lateinit var tvName : TextView
    private lateinit var imageSlider : ImageSlider

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


    }
}