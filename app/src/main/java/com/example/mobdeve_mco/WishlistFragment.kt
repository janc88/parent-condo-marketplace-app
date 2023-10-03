package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mc.GridSpacingItemDecoration
import com.example.mobdeve_mco.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding: FragmentWishlistBinding get() = _binding!!

    private lateinit var rvWishlist: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvWishlist = view.findViewById(R.id.rvWishlist)

        rvWishlist.setHasFixedSize(true)
        rvWishlist.layoutManager = LinearLayoutManager(this.activity)

        val wishlistAdapter = WishlistAdapter(DummyData.listingList)
        rvWishlist.adapter = wishlistAdapter

        wishlistAdapter.onItemClick = {
            val intent = Intent(this.activity, ListingActivity::class.java)
            intent.putExtra("listing", it)
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}