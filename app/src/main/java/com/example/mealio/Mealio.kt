package com.example.mealio

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects

class Mealio (private val context : Context) {
    var uid : String = ""
    var username : String = ""
    var email : String = ""
    var my_recipes : ArrayList<String> = arrayListOf<String>()
    var fav_recipes : ArrayList<String> = arrayListOf<String>()

    private val db = FirebaseFirestore.getInstance()

    constructor(context: Context, user: FirebaseUser) : this(context) {
        this.uid=  user.uid
        this.email = user.email ?: "No email"
        fetchUserData()
    }

    private fun fetchUserData() {
        if (uid.isNotEmpty()) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    this.username = doc.getString("username") ?: "Unknown User"
                    this.my_recipes = doc.get("my_recipes") as ArrayList<String>
                    this.fav_recipes = doc.get("fav_recipes") as ArrayList<String>
                }
        }
    }
}