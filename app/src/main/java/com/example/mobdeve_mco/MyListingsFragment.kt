package com.example.mobdeve_mco

import DummyData
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mc.GridSpacingItemDecoration
import com.example.mobdeve_mco.databinding.FragmentMyListingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MyListingsFragment : Fragment() {
    private var _binding: FragmentMyListingsBinding? = null
    private val binding: FragmentMyListingsBinding get() = _binding!!

    private lateinit var rvMyListings: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListingsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMyListings = view.findViewById(R.id.rvWishlist)

        getCurrentUserListings { listings ->
            val spacingInPixels = 50
            val layoutManager = GridLayoutManager(context, 2)
            rvMyListings.layoutManager = layoutManager
            rvMyListings.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels))

            val myListingsAdapter = MyListingAdapter(listings)
            rvMyListings.adapter = myListingsAdapter

            myListingsAdapter.onItemClick = {
                val intent = Intent(this.activity, ListingActivity::class.java)
                intent.putExtra("listing", it)
                startActivity(intent)
            }
        }


    }

    fun getCurrentUserListings(onComplete: (ArrayList<Listing>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val listingsCollection = db.collection("listings")

            // Query Firestore to get listings with matching ownerId (the current user's ID)
            listingsCollection.whereEqualTo("ownerId", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val userListings = ArrayList<Listing>()

                    for (document in querySnapshot.documents) {
                        val listing = document.toObject(Listing::class.java)
                        if (listing != null) {
                            userListings.add(listing)
                        }
                    }

                    onComplete(userListings)
                }
                .addOnFailureListener { exception ->
                    onComplete(ArrayList()) // Handle errors
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