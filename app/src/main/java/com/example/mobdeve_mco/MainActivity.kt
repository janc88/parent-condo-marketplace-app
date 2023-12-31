package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var bottomNavigationView : BottomNavigationView

    private val exploreFragment = ExploreFragment()
    private val myListingsFragment = MyListingsFragment()
    private val wishlistFragment = WishlistFragment()
    private val accountFragment = AccountFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ExploreFragment())


        val userLoggedIn = intent.getBooleanExtra("userLoggedIn", false)

        if (userLoggedIn) {
            replaceFragment(ExploreFragment())
        }



        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = true


        bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.explore -> replaceFragment(exploreFragment)
                R.id.myListings -> replaceFragment(myListingsFragment)
                R.id.addListing -> {
                    val intent = Intent(this, AddListingStepsActivity::class.java)
                    startActivity(intent)
                }
                R.id.account -> replaceFragment(accountFragment)
                R.id.wishlist -> replaceFragment(wishlistFragment)
                else ->{

                }
            }

            true

        }

        val fragmentTag = intent.getStringExtra("accountFragment")

        if (fragmentTag != null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            when (fragmentTag) {
                "LoggedOutFragment" -> {
                    val fragment = LoggedOutFragment()
                    fragmentTransaction.replace(R.id.frame_layout, fragment)
                    bottomNavigationView.selectedItemId = R.id.account
                }

            }
            fragmentTransaction.commit()
        }

    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()
//    }
}