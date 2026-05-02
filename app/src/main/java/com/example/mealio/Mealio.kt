package com.example.mealio

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects

class Mealio (private val context : Context) {
    var uid : String = ""
    var username : String = ""
    var email : String = ""
    var recipesPosted : MutableList<Recipe> = mutableListOf<Recipe>()
    var recipesSaved : MutableList<Recipe> = mutableListOf<Recipe>()

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
                }

            fetchRecipes("recipesPosted", recipesPosted)
            fetchRecipes("recipesSaved", recipesSaved)
        }
    }

    private fun fetchRecipes(collection : String, list: MutableList<Recipe>) {
        db.collection("users").document(uid).collection(collection)
            .get()
            .addOnSuccessListener { doc ->
                val recipes = doc.toObjects<Recipe>()
                list.clear()
                list.addAll(recipes)
            }
    }
}