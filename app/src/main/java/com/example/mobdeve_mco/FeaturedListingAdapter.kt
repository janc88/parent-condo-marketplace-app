package com.example.mobdeve_mco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext

class FeaturedListingAdapter(private var listings:ArrayList<Listing>) :RecyclerView.Adapter<FeaturedListingAdapter.ListingViewHolder>(){

    class ListingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
    }



    var onItemClick : ((Listing) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.featured_listing_card, parent, false)
        return ListingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]

        holder.tvTitle.text = listing.title
        val imageUrl = listing.imageList[0]
        Picasso.get().load(imageUrl).into(holder.ivImage)
        holder.tvPrice.text = listing.price.formatPrice()

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listing)
        }
    }


}