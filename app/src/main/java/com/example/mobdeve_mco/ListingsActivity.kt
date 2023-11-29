package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingsActivity : AppCompatActivity() {

    private var listingIds: ArrayList<String> = ArrayList()
    private lateinit var propertyName: String
    private lateinit var propertyId: String


    private var listings = ArrayList<Listing>()

    private lateinit var rvSearchResults: RecyclerView
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var tvPropertyName: TextView
    private lateinit var btnBack: CardView

    private val firebaseHelper = FirebaseHelper.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings)


        propertyName = intent.getStringExtra("propertyName")!!
        propertyId = intent.getStringExtra("propertyId")!!


        firebaseHelper.getAvailableListingsForProperty(propertyId){listings ->
            rvSearchResults.setHasFixedSize(true)
            rvSearchResults.layoutManager = LinearLayoutManager(this)

            listingAdapter = ListingAdapter(ArrayList(listings), supportFragmentManager)
            rvSearchResults.adapter = listingAdapter

            listingAdapter.onItemClick = {
                val intent = Intent(this, ListingActivity::class.java)
                intent.putExtra("listing", it)
                startActivity(intent)
            }
        }


        tvPropertyName = findViewById(R.id.tvPropertyName)
        tvPropertyName.text = propertyName

        rvSearchResults= findViewById(R.id.rvSearchResults)


        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            onBackPressed()
        }

    }



}