package com.example.mobdeve_mco

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.R
import com.google.android.material.card.MaterialCardView
import java.io.File

data class ImageItem(val imageUri: Uri)

class ImageAdapter(
    private val addMoreClickListener: OnAddMoreClickListener,
    private val imageRemoveClickListener: ImageRemoveClickListener,
    private val selectedImages: MutableList<ImageItem>
) : ListAdapter<ImageItem, RecyclerView.ViewHolder>(ImageDiffCallback()) {
    private val VIEW_TYPE_IMAGE = 1
    private val VIEW_TYPE_ADD_MORE = 2

    fun removeImage(position: Int) {
        if (position in 0 until selectedImages.size) {
            selectedImages.removeAt(position)
            notifyItemRemoved(position)

            if (position < currentList.size) {
                val imageUri = currentList[position].imageUri
                deleteImageFromStorage(imageUri)
            }
        }
    }

    private fun deleteImageFromStorage(imageUri: Uri) {
        try {
            val imageFile = File(imageUri.path)
            if (imageFile.exists()) {
                imageFile.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
                ImageViewHolder(view, imageRemoveClickListener)
            }
            VIEW_TYPE_ADD_MORE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_add_more, parent, false)
                AddMoreViewHolder(view, addMoreClickListener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < currentList.size) {
            when (holder) {
                is ImageViewHolder -> {
                    val imageUri = getItem(position).imageUri
                    holder.bind(imageUri)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size && currentList.isNotEmpty()) {
            VIEW_TYPE_ADD_MORE
        } else {
            VIEW_TYPE_IMAGE
        }
    }

    override fun getItemCount(): Int {
        return currentList.size + if (currentList.isNotEmpty()) 1 else 0
    }
}


class ImageViewHolder(itemView: View, private val callback: ImageRemoveClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.ivImage)
    private val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)

    fun bind(imageUri: Uri) {
        imageView.setImageURI(imageUri)

        btnRemove.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // Here, we directly remove the item from the selectedImages list and storage.
                callback.onImageRemoveClick(position)
            }
        }
    }
}



class AddMoreViewHolder(itemView: View, private val callback: OnAddMoreClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val cvAddMore: MaterialCardView = itemView.findViewById(R.id.cvAddMore)

    init {
        cvAddMore.setOnClickListener {
            callback.onAddMoreClick()
        }
    }
}

interface OnAddMoreClickListener {
    fun onAddMoreClick()
}

interface ImageRemoveClickListener {
    fun onImageRemoveClick(position: Int)
}


class ImageDiffCallback : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.imageUri == newItem.imageUri
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }
}

