package com.example.mobdeve_mco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListingAdapter(private var listings:ArrayList<Listing>) :RecyclerView.Adapter<ListingAdapter.ListingViewHolder>(){

    class ListingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val ivListingImg : ImageView = itemView.findViewById(R.id.ivListingImg)
        val tvListingTitle : TextView = itemView.findViewById(R.id.tvListingTitle)
    }

    var onItemClick : ((Listing) -> Unit)? = null

    fun setFilteredList(listings: ArrayList<Listing>){
        this.listings = listings
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listing_card, parent, false)
        return ListingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]
        holder.ivListingImg.setImageResource(listing.image)
        holder.tvListingTitle.text = listing.title

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listing)
        }

    }
}