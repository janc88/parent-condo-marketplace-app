package com.example.mobdeve_mco

import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class AddListingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button
    private lateinit var addListingFormAdapter: AddListingFormAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing)

        bindViews()

        init()

        setListeners()
    }

    private fun bindViews(){
        viewPager = findViewById(R.id.viewPager)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
    }

    private fun init(){
        addListingFormAdapter = AddListingFormAdapter(this)
        viewPager.adapter = addListingFormAdapter

        viewPager.isUserInputEnabled = false
        btnBack.paintFlags = btnBack.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun setListeners(){
        btnBack.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }
        }

        btnNext.setOnClickListener {
            if (viewPager.currentItem < addListingFormAdapter.itemCount - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem > 0) {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        } else {
            super.onBackPressed()
        }
    }
}
