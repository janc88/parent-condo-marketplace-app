package com.example.mobdeve_mco

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2


class AddListingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button
    private lateinit var addListingFormAdapter: AddListingFormAdapter
    private lateinit var progressBar: ProgressBar

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
        progressBar = findViewById(R.id.progressBar)
    }

    private fun init(){
        addListingFormAdapter = AddListingFormAdapter(this)
        viewPager.adapter = addListingFormAdapter

        viewPager.isUserInputEnabled = false
        btnBack.paintFlags = btnBack.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val progressDrawable = progressBar.progressDrawable?.mutate()
        progressDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_IN)
        progressBar.progressDrawable = progressDrawable
    }

    private fun setListeners(){
        btnBack.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }
            updateProgressBar()
        }

        btnNext.setOnClickListener {
            if (viewPager.currentItem < addListingFormAdapter.itemCount - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }
            updateProgressBar()
        }

    }

    private fun updateProgressBar(){
        val progress = (viewPager.currentItem * 100) / (addListingFormAdapter.itemCount - 1)
        progressBar.progress = progress
    }

    override fun onBackPressed() {
        if (viewPager.currentItem > 0) {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        } else {
            super.onBackPressed()
        }
    }
}
