package com.example.mobdeve_mco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mobdeve_mco.formatPrice

class WishlistAdapter(private var listings:ArrayList<Listing>) :RecyclerView.Adapter<WishlistAdapter.ListingViewHolder>(){

    private var isLiked = false
    class ListingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageSlider : ImageSlider = itemView.findViewById(R.id.imageSlider)
        val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        val tvNumBedroom : TextView = itemView.findViewById(R.id.tvNumBedroom)
        val tvNumBathroom : TextView = itemView.findViewById(R.id.tvNumBathroom)
        val tvArea : TextView = itemView.findViewById(R.id.tvArea)
        val tvFloor : TextView = itemView.findViewById(R.id.tvFloor)
        val tvPropertyName : TextView = itemView.findViewById(R.id.tvPropertyName)
        val btnHeart : ImageButton = itemView.findViewById(R.id.btnHeart)
        val btnHeartBorder : ImageButton = itemView.findViewById(R.id.btnHeartBorder)

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_card, parent, false)
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
        holder.tvPropertyName.text = listing.property
        holder.tvPrice.text = listing.price.formatPrice()
        holder.tvNumBedroom.text = listing.numBedroom.toString()
        holder.tvNumBathroom.text = listing.numBathroom.toString()
        holder.tvArea.text = listing.area.toString()
        holder.tvFloor.text = "${formatFloor(listing.floor)}"

        holder.setImageSliderClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                onItemClick?.invoke(listing)
            }

            override fun doubleClick(position: Int) {

            }
        })

        holder.btnHeartBorder.setOnClickListener{
            if (isLiked) {
                holder.btnHeart.setBackgroundResource(R.drawable.ic_heart_unliked)
            } else {
                holder.btnHeart.setBackgroundResource(R.drawable.ic_heart_liked)
            }
            isLiked = !isLiked
        }


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listing)
        }
    }

}