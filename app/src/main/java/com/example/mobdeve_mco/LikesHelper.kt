package com.example.mobdeve_mco

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class LikesHelper {

    fun isListingLiked(listingId: String, onResult: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef: DocumentReference = db.collection("users").document(userId)

            userRef.get().addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    val likes = userSnapshot.data?.get("likes") as? List<String> ?: emptyList()
                    val isLiked = likes.contains(listingId)
                    onResult(isLiked)
                } else {
                    // The user document doesn't exist or has no likes
                    onResult(false)
                }
            }.addOnFailureListener { e ->
                // Handle any errors that may occur
                // You can display an error message or perform other error handling here.
                onResult(false)
            }
        } else {
            // The user is not logged in, so they cannot like or unlike listings
            onResult(false)
        }
    }

    fun handleLikeButtonClick(listingId: String, onSuccess: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef: DocumentReference = db.collection("users").document(userId)

            userRef.get().addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    val likes = userSnapshot.data?.get("likes") as? List<String> ?: emptyList()

                    if (likes.contains(listingId)) {
                        // The item is already liked, so remove it from the list
                        userRef.update("likes", FieldValue.arrayRemove(listingId))
                            .addOnSuccessListener {
                                // Unlike button click was successful
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                // Handle any errors that may occur
                                // You can display an error message or perform other error handling here.
                            }
                    } else {
                        // The item is not liked, so add it to the list
                        userRef.update("likes", FieldValue.arrayUnion(listingId))
                            .addOnSuccessListener {
                                // Like button click was successful
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                // Handle any errors that may occur
                                // You can display an error message or perform other error handling here.
                            }
                    }
                }
            }
        } else {
            // The user is not logged in. Handle the case where the user is not authenticated.
            // You can show a message or redirect them to the login screen.
        }
    }
}
