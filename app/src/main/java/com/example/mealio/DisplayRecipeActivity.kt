package com.example.mealio

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayRecipeActivity : AppCompatActivity() {
    private lateinit var recipe_name : TextView
    private lateinit var ingredients : TextView
    private lateinit var steps : TextView
    private lateinit var share : Button
    private lateinit var back : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        recipe_name = findViewById<TextView>(R.id.recipe_name)
        ingredients = findViewById<TextView>(R.id.ingredients)
        steps = findViewById<TextView>(R.id.steps)

        share = findViewById<Button>(R.id.share)
        back = findViewById<Button>(R.id.back)

        back.setOnClickListener { finish() }

    }

}