package com.example.mealio

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DisplayRecipeActivity : AppCompatActivity() {
    private lateinit var recipe_name : TextView
    private lateinit var ingredients : TextView
    private lateinit var description : TextView
    private lateinit var steps : TextView
    private lateinit var share : Button
    private lateinit var back : ImageButton
    private lateinit var favorite : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        recipe_name = findViewById<TextView>(R.id.recipe_name)
        ingredients = findViewById<TextView>(R.id.ingredients)
        description = findViewById<TextView>(R.id.description)
        steps = findViewById<TextView>(R.id.steps)

        share = findViewById<Button>(R.id.share)
        back = findViewById<ImageButton>(R.id.back)
        favorite = findViewById<Button>(R.id.favorite)

        back.setOnClickListener { finish() }
        favorite.setOnClickListener { MainActivity.mealio!!.favorite_recipe(recipe_name.text.toString()) }

        val recipe_key = intent.getStringExtra("recipe")
        val name = recipe_key!!.substringBefore(":").trim()
        recipe_name.text = name

        val db : FirebaseFirestore = MainActivity.mealio!!.get_db()

        db.collection("recipes").document(recipe_key).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val desc = (doc.get("description") as CharSequence?)
                    val ingr = (doc.get("ingredients") as? List<String>)?.joinToString("\n") ?: ""
                    val step = (doc.get("instructions") as? List<String>)?.joinToString("\n") ?: ""
                    description.text = desc
                    ingredients.text = ingr
                    steps.text = step

                    share.setOnClickListener {
                        MainActivity.mealio?.emailRecipe(name, desc.toString(), ingr, step)
                    }
                }


            }
    }
}
