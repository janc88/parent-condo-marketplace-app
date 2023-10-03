package com.example.mobdeve_mco

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView


class MyListingAdapter(private var listings:ArrayList<Listing>) :RecyclerView.Adapter<MyListingAdapter.ListingViewHolder>(){

    class ListingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        val tvNumBedroom : TextView = itemView.findViewById(R.id.tvNumBedroom)
        val tvNumBathroom : TextView = itemView.findViewById(R.id.tvNumBathroom)
        val tvArea : TextView = itemView.findViewById(R.id.tvArea)
        val tvFloor : TextView = itemView.findViewById(R.id.tvFloor)
        val btnMarkAsRented: Button = itemView.findViewById(R.id.btnMarkAsRented)
        val tvIsRented: TextView = itemView.findViewById(R.id.tvIsRented)

        val mainCard: LinearLayout = itemView.findViewById(R.id.mainCard)
        val imgCard: CardView = itemView.findViewById(R.id.imgCard)

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

        if(!listing.isRented){
            holder.tvIsRented.isVisible = false
        }
        holder.ivImage.setImageResource(listing.imageList[0])
        holder.tvPrice.text = listing.price.formatPrice()
        holder.tvNumBedroom.text = listing.numBedroom.toString()
        holder.tvNumBathroom.text = listing.numBathroom.toString()
        holder.tvArea.text = listing.area.toString()
        holder.tvFloor.text = formatFloor(listing.floor)

        holder.btnMarkAsRented.setOnClickListener{

        }


    }

}