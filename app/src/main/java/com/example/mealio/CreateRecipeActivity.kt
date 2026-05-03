package com.example.mealio

import android.content.Context
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreateRecipeActivity : AppCompatActivity() {

    private lateinit var etDescription: EditText
    private lateinit var ingredientsContainer: LinearLayout
    private lateinit var btnAddIngredient: Button
    private lateinit var instructionsContainer: LinearLayout
    private lateinit var btnAddInstruction: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnRemoveIngredient: Button
    private lateinit var btnRemoveInstruction: Button

    private var ingredientCount = 1
    private var stepCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)

        //define views
        etDescription = findViewById(R.id.etDescription)
        ingredientsContainer = findViewById(R.id.ingredientsContainer)
        btnAddIngredient = findViewById(R.id.btnAddIngredient)
        instructionsContainer = findViewById(R.id.instructionsContainer)
        btnAddInstruction = findViewById(R.id.btnAddInstruction)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnRemoveIngredient = findViewById(R.id.btnRemoveIngredient)
        btnRemoveInstruction = findViewById(R.id.btnRemoveInstruction)

        // Add ingredient
        btnAddIngredient.setOnClickListener {
            ingredientCount++
            val field = createInputField(context = this, hint = "ingredient $ingredientCount", multiLine = false)
            ingredientsContainer.addView(field)
            field.requestFocus()
            btnRemoveIngredient.visibility = View.VISIBLE
        }

        // Remove last ingredient
        btnRemoveIngredient.setOnClickListener {
            if (ingredientsContainer.childCount > 1) {
                ingredientsContainer.removeViewAt(ingredientsContainer.childCount - 1)
                ingredientCount--
            }
            if (ingredientsContainer.childCount <= 1) {
                btnRemoveIngredient.visibility = View.GONE
            }
        }

        // Add instruction
        btnAddInstruction.setOnClickListener {
            stepCount++
            val field = createInputField(context = this, hint = "Step $stepCount", multiLine = true)
            instructionsContainer.addView(field)
            field.requestFocus()
            btnRemoveInstruction.visibility = View.VISIBLE
        }

        // Remove last instruction
        btnRemoveInstruction.setOnClickListener {
            if (instructionsContainer.childCount > 1) {
                instructionsContainer.removeViewAt(instructionsContainer.childCount - 1)
                stepCount--
            }
            if (instructionsContainer.childCount <= 1) {
                btnRemoveInstruction.visibility = View.GONE
            }
        }

        //setup submit button
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                buildRecipe()
                submitRecipe()
            }
        }
    }


    private fun createInputField(context: Context, hint: String, multiLine: Boolean): EditText {
        return EditText(context).apply {
            this.hint = hint
            textSize = 14f
            setPadding(
                (12 * resources.displayMetrics.density).toInt(),
                (12 * resources.displayMetrics.density).toInt(),
                (12 * resources.displayMetrics.density).toInt(),
                (12 * resources.displayMetrics.density).toInt()
            )
            if (multiLine) {
                minLines = 2
                maxLines = 4
                gravity = Gravity.TOP or Gravity.START
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            } else {
                inputType = InputType.TYPE_CLASS_TEXT
            }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = (8 * resources.displayMetrics.density).toInt()
                bottomMargin = (8 * resources.displayMetrics.density).toInt()
            }
        }
    }

    private fun validateForm(): Boolean {
        // Description
        if (etDescription.text.isBlank()) {
            etDescription.error = "Please enter a description"
            etDescription.requestFocus()
            return false
        }

        // Ingredients — at least the first field must be filled
        val firstIngredient = ingredientsContainer.getChildAt(0) as? EditText
        if (firstIngredient == null || firstIngredient.text.isBlank()) {
            firstIngredient?.error = "Please enter at least one ingredient"
            firstIngredient?.requestFocus()
            return false
        }

        // Check all visible ingredient fields are non-empty
        for (i in 0 until ingredientsContainer.childCount) {
            val field = ingredientsContainer.getChildAt(i) as? EditText
            if (field != null && field.text.isBlank()) {
                field.error = "Please fill in this ingredient or remove it"
                field.requestFocus()
                return false
            }
        }

        // Instructions — at least the first step must be filled
        val firstStep = instructionsContainer.getChildAt(0) as? EditText
        if (firstStep == null || firstStep.text.isBlank()) {
            firstStep?.error = "Please enter at least one instruction step"
            firstStep?.requestFocus()
            return false
        }

        // Check all visible instruction fields are non-empty
        for (i in 0 until instructionsContainer.childCount) {
            val field = instructionsContainer.getChildAt(i) as? EditText
            if (field != null && field.text.isBlank()) {
                field.error = "Please fill in this step or remove it"
                field.requestFocus()
                return false
            }
        }

        return true
    }

    private fun buildRecipe() {

    }

    private fun submitRecipe() {
        Toast.makeText(this, "Recipe submitted!", Toast.LENGTH_SHORT).show()
    }
}