package com.example.mealio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

class DisplayAllActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val recipes = intent.getStringArrayListExtra("all_recipes") ?: return

        for(recipe in recipes){
            val button = Button(this)
            button.tag = recipe
            button.text = recipe.substringBefore(":")

            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            button.setOnClickListener {
                val intent = Intent(this, DisplayRecipeActivity::class.java)
                intent.putExtra("recipe", button.tag as String)
                startActivity(intent)
            }

            layout.addView(button)
        }

        scrollView.addView(layout)
        setContentView(scrollView)

    }
}