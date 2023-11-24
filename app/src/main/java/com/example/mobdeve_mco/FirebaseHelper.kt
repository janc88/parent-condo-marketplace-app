package com.example.mobdeve_mco

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelper {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Firebase Authentication

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun signIn(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun signOut() {
        auth.signOut()
    }

    // Firestore Database

    fun getUserDocument(userId: String): DocumentReference {
        return db.collection("users").document(userId)
    }

    fun getCurrentUserDocument(): DocumentReference? {
        val currentUser = getCurrentUser()
        return currentUser?.uid?.let { getUserDocument(it) }
    }

    fun addOrUpdateUser(userId: String, data: Map<String, Any>, onComplete: (Boolean) -> Unit) {
        getUserDocument(userId).set(data)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun toggleRentedStatus(listingId: String, onComplete: (Boolean?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val listingsCollection = db.collection("listings")

        val listingRef: DocumentReference = listingsCollection.document(listingId)

        listingRef.get().addOnSuccessListener { listingSnapshot ->
            if (listingSnapshot.exists()) {
                val currentRentedStatus = listingSnapshot.getBoolean("rented") ?: false
                val updatedRentedStatus = !currentRentedStatus

                // Update the 'rented' field in the listing document
                listingRef.update("rented", updatedRentedStatus)
                    .addOnSuccessListener {
                        onComplete(updatedRentedStatus) // Return the new 'rented' status
                    }
                    .addOnFailureListener { e ->
                        onComplete(null) // Update failed, return null
                    }
            } else {
                onComplete(null) // Listing not found, return null
            }
        }
    }

    fun getProperties(onComplete: (List<Property>) -> Unit) {
        db.collection("properties")
            .get()
            .addOnSuccessListener { result ->
                val properties = mutableListOf<Property>()
                for (document in result) {
                    val property = document.toObject(Property::class.java)
                    properties.add(property)
                }
                onComplete(properties)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())
            }
    }

    fun getAvailableListingsForProperty(propertyId: String, onListingsReceived: (List<Listing>) -> Unit) {
        val listingsRef = db.collection("listings")

        listingsRef
            .whereEqualTo("propertyId", propertyId)
            .whereEqualTo("rented", false)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val matchingListings = mutableListOf<Listing>()

                for (document in querySnapshot.documents) {
                    val listingData = document.toObject(Listing::class.java)
                    if (listingData != null) {
                        matchingListings.add(listingData)
                    }
                }

                onListingsReceived(matchingListings)
            }
            .addOnFailureListener { e ->
                onListingsReceived(emptyList())
            }
    }

    // Add more Firestore operations as needed

    // Singleton pattern for FirebaseHelper
    companion object {
        private var instance: FirebaseHelper? = null

        fun getInstance(): FirebaseHelper {
            if (instance == null) {
                instance = FirebaseHelper()
            }
            return instance as FirebaseHelper
        }
    }
}
