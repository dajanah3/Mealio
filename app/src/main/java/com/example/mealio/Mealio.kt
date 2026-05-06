package com.example.mealio

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Mealio (private val context : Context) {
    var uid : String = ""
    var user_info : HashMap<String, Any>? = null

    private val db = FirebaseFirestore.getInstance()

    constructor(context: Context, user: FirebaseUser) : this(context) {
        this.uid=  user.uid
//        this.email = user.email ?: "No email"
        fetchUserData()
    }

    private fun fetchUserData() {
        if (uid.isNotEmpty()) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    val data = doc.data
                    if (data != null) {
                        this.user_info = HashMap(data)
                        Log.w("MainActivity", "user_info loaded and not null")
                        val intent = Intent(context, HomePage::class.java)
                        context.startActivity(intent)
                    }
                }
        }
    }

    fun save_recipe(name: String, recipe: HashMap<String, Any>) {
        val key = "$name : $uid"

        db.collection("recipes").document(key).set(recipe)

        db.collection("users").document(uid)
            .update("my_recipes", FieldValue.arrayUnion(key))
    }

    fun save_keyword(keyword: String, recipe_title: String) {
        val docRef = db.collection("keywords").document(keyword)
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Keyword exists, retrieve the list and append
                val recipes = document.get("recipes") as? ArrayList<String> ?: arrayListOf()
                recipes.add("$recipe_title : $uid")
                docRef.update("recipes", recipes)
            } else {
                // Keyword doesn't exist, create it with a new list
                val newList = arrayListOf("$recipe_title : $uid")
                docRef.set(hashMapOf("recipes" to newList))
            }
        }
    }


}