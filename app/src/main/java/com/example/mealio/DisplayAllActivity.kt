package com.example.mealio

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DisplayAllActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_all)

        val back = findViewById<ImageButton>(R.id.back)
        val list = findViewById<LinearLayout>(R.id.recipe_list)
        val empty = findViewById<TextView>(R.id.empty_state)

        back.setOnClickListener { finish() }

        val recipes = intent.getStringArrayListExtra("recipe_keys") ?: arrayListOf()

        if (recipes.isEmpty()) {
            empty.visibility = TextView.VISIBLE
            return
        }

        val itemHeight = dp(64)
        val itemMargin = dp(10)
        val sidePadding = dp(18)

        for (recipe in recipes) {
            val button = MaterialButton(this).apply {
                tag = recipe
                text = recipe.substringBefore(":")
                isAllCaps = false
                gravity = Gravity.CENTER_VERTICAL or Gravity.START
                setTextColor(0xFF1C1C1E.toInt())
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setPadding(sidePadding, 0, sidePadding, 0)
                insetTop = 0
                insetBottom = 0
                cornerRadius = dp(18)
                strokeWidth = 0
                elevation = 0f
                backgroundTintList = android.content.res.ColorStateList.valueOf(0xFFF2F2F7.toInt())

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    itemHeight
                )
                params.bottomMargin = itemMargin
                layoutParams = params

                setOnClickListener {
                    val intent = Intent(this@DisplayAllActivity, DisplayRecipeActivity::class.java)
                    intent.putExtra("recipe", recipe)
                    startActivity(intent)
                }
            }

            list.addView(button)
        }
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
