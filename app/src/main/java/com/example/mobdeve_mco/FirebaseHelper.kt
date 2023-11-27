package com.example.mobdeve_mco

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


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

    fun getSimilarAvailableListings(listing: Listing, onListingsReceived: (List<Listing>) -> Unit) {
        val listingsRef = db.collection("listings")

        listingsRef.whereEqualTo("propertyId", listing.propertyId)
            .whereEqualTo("rented", false)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val similarListings = mutableListOf<Listing>()

                for (document in querySnapshot.documents) {
                    if (document.id != listing.id) {
                        val listingData = document.toObject(Listing::class.java)
                        if (listingData != null) {
                            similarListings.add(listingData)
                        }
                    }
                }

                onListingsReceived(similarListings)
            }
            .addOnFailureListener { e ->
                onListingsReceived(emptyList())
            }
    }

    fun getCurrentUserListings(onComplete: (ArrayList<Listing>) -> Unit) {
        val currentUser = getCurrentUser()

        if (currentUser != null) {
            val userId = currentUser.uid
            val listingsCollection = db.collection("listings")

            // Query Firestore to get listings with matching ownerId (the current user's ID)
            listingsCollection.whereEqualTo("ownerId", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val userListings = ArrayList<Listing>()

                    for (document in querySnapshot.documents) {
                        val listing = document.toObject(Listing::class.java)
                        if (listing != null) {
                            userListings.add(listing)
                        }
                    }

                    onComplete(userListings)
                }
                .addOnFailureListener { exception ->
                    onComplete(ArrayList()) // Handle errors
                }
        } else {
            onComplete(ArrayList()) // User is not logged in
        }
    }

    fun getUserFromFirestore(userId: String, onUserReceived: (User?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val userDocumentRef: DocumentReference = usersCollection.document(userId)

        userDocumentRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document: DocumentSnapshot? = task.result

                    if (document != null && document.exists()) {
                        val user = document.toObject(User::class.java)
                        onUserReceived(user)
                    } else {
                        onUserReceived(null)
                    }
                } else {
                    val exception: Exception? = task.exception
                    onUserReceived(null)
                }
            }
    }

    fun getPropertyFromFirestore(propertyId: String, onPropertyReceived: (Property?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val propertiesCollection = db.collection("properties")

        val propertyDocumentRef: DocumentReference = propertiesCollection.document(propertyId)

        propertyDocumentRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document: DocumentSnapshot? = task.result

                    if (document != null && document.exists()) {
                        val property = document.toObject(Property::class.java)
                        onPropertyReceived(property)
                    } else {
                        onPropertyReceived(null)
                    }

                } else {
                    val exception: Exception? = task.exception
                    onPropertyReceived(null)

                }
            }
    }

    fun updateProfile(
        bio: String,
        contactNum: String,
        imageUri: Uri?,
        onComplete: (Boolean) -> Unit
    ) {
        val currentUser = getCurrentUser()

        if (currentUser != null) {
            val storageReference = FirebaseStorage.getInstance().reference
            val profilePictureRef: StorageReference =
                storageReference.child("profile_pictures/${currentUser.uid}.jpg")

            val userDocument = getUserDocument(currentUser.uid)

            val dataToUpdate = mutableMapOf<String, Any>(
                "bio" to bio,
                "contactNum" to contactNum
                // Add other fields you want to update here
            )

            if (imageUri != null) {
                // Update the profile picture if imageUri is provided
                profilePictureRef.putFile(imageUri)
                    .addOnSuccessListener {
                        profilePictureRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                            val downloadUrlString = downloadUrl.toString()
                            dataToUpdate["pfp"] = downloadUrlString

                            // Update the user document with the new data
                            userDocument.update(dataToUpdate)
                                .addOnSuccessListener {
                                    onComplete(true)
                                }
                                .addOnFailureListener {
                                    onComplete(false)
                                }
                        }
                    }
                    .addOnFailureListener {
                        onComplete(false)
                    }
            } else {
                // If no new image is provided, update other fields only
                userDocument.update(dataToUpdate)
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        onComplete(false)
                    }
            }
        } else {
            onComplete(false) // User is not logged in
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
