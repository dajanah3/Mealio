package com.example.mealio

import android.content.Context

class Mealio (private val context : Context) {
    private var username : String = ""
    private var email : String = ""
    private lateinit var recipesAdded : Array<Recipe>
    private lateinit var recipesSaved : Array<Recipes>


    constructor(context: Context, user : String) : this(context) {
        username = user
    }
}