package com.example.mealio

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayRecipeActivity : AppCompatActivity() {
    private lateinit var recipe_name : TextView
    private lateinit var ingredients : TextView
    private lateinit var steps : TextView
    private lateinit var share : Button
    private lateinit var back : ImageButton
    private lateinit var favorite : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        recipe_name = findViewById<TextView>(R.id.recipe_name)
        ingredients = findViewById<TextView>(R.id.ingredients)
        steps = findViewById<TextView>(R.id.steps)

        share = findViewById<Button>(R.id.share)
        back = findViewById<ImageButton>(R.id.back)
        favorite = findViewById<Button>(R.id.favorite)

        back.setOnClickListener { finish() }
        favorite.setOnClickListener { MainActivity.mealio!!.favorite_recipe(recipe_name.text.toString()) }
    }
}
