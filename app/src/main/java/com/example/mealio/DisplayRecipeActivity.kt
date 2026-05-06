package com.example.mealio

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayRecipeActivity : AppCompatActivity() {
    private var recipe_name = findViewById<TextView>(R.id.recipe_name)
    private var ingredients = findViewById<TextView>(R.id.ingredients)
    private var steps = findViewById<TextView>(R.id.steps)

    private var share = findViewById<Button>(R.id.share)
    private var back = findViewById<Button>(R.id.back)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

    }



}