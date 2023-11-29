package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentWishlistLoggedInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class WishlistLoggedIn : Fragment() {
    private var _binding: FragmentWishlistLoggedInBinding? = null
    private val binding: FragmentWishlistLoggedInBinding get() = _binding!!

    private lateinit var rvWishlist : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistLoggedInBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWishlist = view.findViewById(R.id.rvMyListings)

        getWishlistFromFirestore(){ listings ->

            if(listings.isNotEmpty() && isAdded()){
                rvWishlist.setHasFixedSize(true)
                rvWishlist.layoutManager = LinearLayoutManager(this.activity)

                val wishlistAdapter = WishlistAdapter(listings, requireActivity().supportFragmentManager)
                rvWishlist.adapter = wishlistAdapter

                wishlistAdapter.onItemClick = {
                    val intent = Intent(this.activity, ListingActivity::class.java)
                    intent.putExtra("listing", it)
                    startActivity(intent)
                }
            }
        }
    }

    fun getWishlistFromFirestore(onComplete: (ArrayList<Listing>) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef: DocumentReference = db.collection("users").document(userId)

            userRef.get().addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    val likes = userSnapshot.data?.get("likes") as? ArrayList<String>
                    if (likes != null && likes.isNotEmpty()) {
                        val listingsCollection = db.collection("listings")
                        val likedListings = ArrayList<Listing>()

                        // Fetch listings with matching IDs
                        listingsCollection.whereIn("id", likes)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                for (document in querySnapshot.documents) {
                                    val listing = document.toObject(Listing::class.java)
                                    if (listing != null) {
                                        likedListings.add(listing)
                                    }
                                }

                                onComplete(likedListings)
                            }
                            .addOnFailureListener { exception ->
                                onComplete(ArrayList()) // Handle errors
                            }
                    } else {
                        onComplete(ArrayList()) // No liked listings
                    }
                } else {
                    onComplete(ArrayList()) // User document doesn't exist
                }
            }.addOnFailureListener { e ->
                onComplete(ArrayList()) // Handle any errors that may occur
            }
        } else {
            onComplete(ArrayList()) // User is not logged in
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}