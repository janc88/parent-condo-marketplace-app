package com.example.mobdeve_mco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class ListingAdapter(private var listings:ArrayList<Listing>) :RecyclerView.Adapter<ListingAdapter.ListingViewHolder>(){

    class ListingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageSlider : ImageSlider = itemView.findViewById(R.id.imageSlider)
        val tvListingTitle : TextView = itemView.findViewById(R.id.tvListingTitle)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        val tvProperty : TextView = itemView.findViewById(R.id.tvProperty)
        val tvUniversity : TextView = itemView.findViewById(R.id.tvUniversity)
        fun setImageSliderClickListener(itemClickListener: ItemClickListener) {
            imageSlider.setItemClickListener(itemClickListener)
        }
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

        val imageList = ArrayList<SlideModel>()

        for(image in listing.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }

        holder.imageSlider.setImageList(imageList)
        holder.tvListingTitle.text = listing.title
        holder.tvPrice.text = listing.price.formatPrice()
        holder.tvUniversity.text = listing.university
        holder.tvUniversity.setUniversityStyle(listing.university)
        holder.tvProperty.text = listing.property

        holder.setImageSliderClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                onItemClick?.invoke(listing)
            }

            override fun doubleClick(position: Int) {

            }
        })


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listing)
        }
    }

    private fun Int.formatPrice(): String {
        val formatter = java.text.DecimalFormat("#,###")
        return "₱" + formatter.format(this.toLong())
    }

    private fun TextView.setUniversityStyle(university: String) {
        val backgroundResource = when (university) {
            "DLSU" -> R.drawable.border_dlsu
            "UST" -> R.drawable.border_ust
            "UP" -> R.drawable.border_up
            "ADMU" -> R.drawable.border_admu

            else -> R.drawable.border_dlsu
        }

        val textColorResId = when (university) {
            "DLSU" -> R.color.dlsu_color
            "UST" -> R.color.ust_color
            "UP" -> R.color.up_color
            "ADMU" -> R.color.admu_color

            else -> R.color.black
        }

        val textColor = ContextCompat.getColor(context, textColorResId)

        setBackgroundResource(backgroundResource)
        setTextColor(textColor)
    }

}