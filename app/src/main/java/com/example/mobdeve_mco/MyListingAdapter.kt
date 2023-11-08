package com.example.mobdeve_mco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MyListingAdapter(private var listings:ArrayList<Listing>) :RecyclerView.Adapter<MyListingAdapter.ListingViewHolder>(){

    class ListingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)
        val tvPropertyName : TextView = itemView.findViewById(R.id.tvPropertyName)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        val tvNumBedroom : TextView = itemView.findViewById(R.id.tvNumBedroom)
        val tvNumBathroom : TextView = itemView.findViewById(R.id.tvNumBathroom)
        val tvArea : TextView = itemView.findViewById(R.id.tvArea)
        val tvFloor : TextView = itemView.findViewById(R.id.tvFloor)
        val btnMarkAsRented: Button = itemView.findViewById(R.id.btnMarkAsRented)
        val tvIsRented: TextView = itemView.findViewById(R.id.tvIsRented)


    }


    var onItemClick : ((Listing) -> Unit)? = null

    fun setFilteredList(listings: ArrayList<Listing>){
        this.listings = listings
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_listing_card, parent, false)
        return ListingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]

        if(!listing.rented){
            holder.tvIsRented.isVisible = false
        }
        val imageUrl = listing.imageList[0]
        Picasso.get().load(imageUrl).resize(500,500).centerCrop().into(holder.ivImage)

        holder.tvPropertyName.text = listing.property
        holder.tvPrice.text = listing.price.formatPrice()
        holder.tvNumBedroom.text = listing.numBedroom.toString()
        holder.tvNumBathroom.text = listing.numBathroom.toString()
        holder.tvArea.text = listing.area.toInt().toString()
        holder.tvFloor.text = formatFloor(listing.floor)

        holder.btnMarkAsRented.setOnClickListener{

        }

        holder.ivImage.setOnClickListener{
            onItemClick?.invoke(listing)
        }


    }

}