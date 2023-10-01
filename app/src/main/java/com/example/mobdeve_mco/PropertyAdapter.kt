package com.example.mobdeve_mco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class PropertyAdapter(private var properties:ArrayList<Property>) :RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>(){

    class PropertyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageSlider : ImageSlider = itemView.findViewById(R.id.imageSlider)
        val tvAddress : TextView = itemView.findViewById(R.id.tvAddress)
        val tvPriceRange : TextView = itemView.findViewById(R.id.tvPriceRange)
        val tvProperty : TextView = itemView.findViewById(R.id.tvProperty)
        val tvUniversity : TextView = itemView.findViewById(R.id.tvUniversity)
        val tvNumListings : TextView = itemView.findViewById(R.id.tvNumListings)
        fun setImageSliderClickListener(itemClickListener: ItemClickListener) {
            imageSlider.setItemClickListener(itemClickListener)
        }
    }

    var onItemClick : ((Property) -> Unit)? = null

    fun setFilteredList(properties:ArrayList<Property>){
        this.properties = properties
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_card, parent, false)
        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]

        val imageList = ArrayList<SlideModel>()

        for(image in property.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }

        holder.imageSlider.setImageList(imageList)
        holder.tvProperty.text = property.name

        val formattedLowestPrice = property.lowestPrice.formatPrice()
        val formattedHighestPrice = property.highestPrice.formatPrice()

        val formattedPriceRange = "$formattedLowestPrice - $formattedHighestPrice"

        holder.tvPriceRange.text = formattedPriceRange
        holder.tvAddress.text = property.address
        holder.tvNumListings.text = "${property.numListings} listings available"
        holder.tvUniversity.text = property.university
        holder.tvUniversity.setUniversityStyle(property.university)

        holder.setImageSliderClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                onItemClick?.invoke(property)
            }

            override fun doubleClick(position: Int) {

            }
        })


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(property)
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