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
