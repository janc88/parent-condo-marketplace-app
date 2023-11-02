package com.example.mobdeve_mco

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
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
            } else{
                // user clicks back on the first page
                showConfirmationDialog()
            }
            updateProgressBar()
        }

        btnNext.setOnClickListener {
            if (viewPager.currentItem < addListingFormAdapter.itemCount - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            } else {
                // user clicks next on the last page
                Log.d("hello", "hello world")
            }
            updateProgressBar()
        }

    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to go back? Your changes will not be saved.")


        builder.setNegativeButton("Go Back") { dialog: DialogInterface, which: Int ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        builder.setPositiveButton("Continue Editing") { dialog: DialogInterface, which: Int ->
            clearSharedPreferences()
        }

        val dialog = builder.create()
        dialog.show()

        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negativeButton.setTextColor(Color.BLACK)

    }


    private fun updateProgressBar(){
        val progress = (viewPager.currentItem * 100) / (addListingFormAdapter.itemCount - 1)
        progressBar.progress = progress
    }

    override fun onBackPressed() {
        if (viewPager.currentItem > 0) {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        } else {
            // user clicks back on the first page
            showConfirmationDialog()
        }
    }

    private fun clearSharedPreferences(){
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}
