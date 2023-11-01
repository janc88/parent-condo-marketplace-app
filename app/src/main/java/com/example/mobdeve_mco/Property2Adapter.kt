package com.example.mobdeve_mco

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class Property2Adapter(private var properties: ArrayList<Property>) : RecyclerView.Adapter<Property2Adapter.PropertyViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var sharedPreferences: SharedPreferences? = null

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvPropertyName: TextView = itemView.findViewById(R.id.tvPropertyName)
    }

    var onItemClick: ((Property) -> Unit)? = null

    fun setFilteredList(properties: ArrayList<Property>) {
        this.properties = properties
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_card2, parent, false)
        sharedPreferences = parent.context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]

        holder.tvPropertyName.text = property.name
        holder.tvAddress.text = property.address
        holder.ivImage.setImageResource(property.imageList[0])

        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.round_10_card_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.round_10_card)
        }

        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            onItemClick?.invoke(property)

            sharedPreferences?.edit()?.putInt("property", property.id)?.apply()
            sharedPreferences?.edit()?.putInt("propertyPosition", selectedPosition)?.apply()

        }
    }
}
