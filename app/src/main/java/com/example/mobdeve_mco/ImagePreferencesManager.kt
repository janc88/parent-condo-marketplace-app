package com.example.mobdeve_mco

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri

class ImagePreferencesManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("image_prefs", Context.MODE_PRIVATE)

    fun saveImage(imageUri: Uri) {
        val editor = sharedPreferences.edit()
        val imageUriString = imageUri.toString()
        val imageCount = sharedPreferences.getInt("image_count", 0)

        editor.putString("image_$imageCount", imageUriString)
        editor.putInt("image_count", imageCount + 1)

        editor.apply()
    }

    fun getImages(): List<Uri> {
        val imageUris: MutableList<Uri> = mutableListOf()
        val imageCount = sharedPreferences.getInt("image_count", 0)

        for (index in 0 until imageCount) {
            val imageUriString = sharedPreferences.getString("image_$index", null)
            if (imageUriString != null) {
                imageUris.add(Uri.parse(imageUriString))
            }
        }

        return imageUris
    }

    fun removeImage(imageUri: Uri) {
        val editor = sharedPreferences.edit()
        val imageUriString = imageUri.toString()
        val imageCount = sharedPreferences.getInt("image_count", 0)

        for (index in 0 until imageCount) {
            val key = "image_$index"
            val storedImageUriString = sharedPreferences.getString(key, null)

            if (imageUriString == storedImageUriString) {
                editor.remove(key)
            }
        }

        editor.apply()
    }

    fun grantPersistableUriPermissions(imageUri: Uri) {
        val contentResolver = context.contentResolver
        contentResolver.takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
}

