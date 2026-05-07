package com.example.mealio

import android.content.Context
import android.content.SharedPreferences
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton

class CreateRecipeActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var ingredientsContainer: LinearLayout
    private lateinit var btnAddIngredient: Button
    private lateinit var instructionsContainer: LinearLayout
    private lateinit var btnAddInstruction: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnRemoveIngredient: Button
    private lateinit var btnRemoveInstruction: Button
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var ingredientCount = 1
    private var stepCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)

        //define views
        btnBack = findViewById(R.id.create_back)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        ingredientsContainer = findViewById(R.id.ingredientsContainer)
        btnAddIngredient = findViewById(R.id.btnAddIngredient)
        instructionsContainer = findViewById(R.id.instructionsContainer)
        btnAddInstruction = findViewById(R.id.btnAddInstruction)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnRemoveIngredient = findViewById(R.id.btnRemoveIngredient)
        btnRemoveInstruction = findViewById(R.id.btnRemoveInstruction)

        sp = this.getSharedPreferences(this.packageName + "_preferences", Context.MODE_PRIVATE)
        editor = sp.edit()

        // Cancel. If REMEMBER, save the current title, description, ingredients (as a set), and instructions (as a set)
            // index i is put in ingredients + instructions to prevent set deletion of duplicates


        btnBack.setOnClickListener {
            if(sp.getBoolean("REMEMBER", false)) {

                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()

                val ingredients = List(ingredientsContainer.childCount) { i ->
                    val field = ingredientsContainer.getChildAt(i) as EditText
                     "$i" + field.text.toString().trim()
                }.toSet()
                val instructions = List(instructionsContainer.childCount) {i ->
                    val field = instructionsContainer.getChildAt(i) as EditText
                    "$i" + field.text.toString().trim()
                }.toSet()

                editor.putString("TITLE", title).putString("DESCRIPTION", description).putStringSet("INGREDIENTS", ingredients).putStringSet("INSTRUCTIONS", instructions)
                    .commit()
            }
            finish()
        }

        // If REMEMBER, prepopulate fields with stored values
            // Remember to snip 1st letter of ingredient/instruction
        if(sp.getBoolean("REMEMBER", false)) {

            etTitle.setText(sp.getString("TITLE", ""))
            etDescription.setText(sp.getString("DESCRIPTION", ""))

            // Ingredients
            val ingredients = sp.getStringSet("INGREDIENTS", mutableSetOf<String>())
            if(ingredients != null) {
                for ((idx, ingredient) in ingredients.withIndex()) {
                    // If not first ingredient
                    if(idx != 0) {
                        ingredientCount++
                        val field = createInputField(
                            context = this,
                            hint = "ingredient $ingredientCount",
                            multiLine = false
                        )
                        ingredientsContainer.addView(field)
                        btnRemoveIngredient.visibility = View.VISIBLE
                    }
                    val ingredientField = ingredientsContainer.getChildAt(idx) as? EditText
                    ingredientField?.setText(ingredient.substring(1))
                }
            }

            val instructions = sp.getStringSet("INSTRUCTIONS", mutableSetOf<String>())
            if(instructions != null) {
                for ((idx, instruction) in instructions.withIndex()) {
                    // If not first instruction
                    if(idx != 0) {
                        stepCount++
                        val field = createInputField(
                            context = this,
                            hint = "Step $stepCount",
                            multiLine = true
                        )
                        instructionsContainer.addView(field)
                        btnRemoveInstruction.visibility = View.VISIBLE
                    }
                    val instructionField = instructionsContainer.getChildAt(idx) as? EditText
                    instructionField?.setText(instruction.substring(1))
                }
            }


        }

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
            Log.w("MainActivity", "recipe submit button clicked")
            if (validateForm()) {
                Log.w("MainActivity", "recipe validated")
                val map = buildRecipe()
                Log.w("MainActivity", "recipe built")
                submitRecipe(map)
                Log.w("MainActivity", "recipe submit")
                finish()
            }
        }
    }


    private fun createInputField(context: Context, hint: String, multiLine: Boolean): EditText {
        return EditText(context).apply {
            this.hint = hint
            textSize = 15f
            setTextColor(0xFF1C1C1E.toInt())
            setHintTextColor(0xFF8E8E93.toInt())
            background = androidx.core.content.ContextCompat.getDrawable(context, R.drawable.bg_input_field)
            val pad = (14 * resources.displayMetrics.density).toInt()
            setPadding(pad, pad, pad, pad)
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
                bottomMargin = (8 * resources.displayMetrics.density).toInt()
            }
        }
    }

    private fun validateForm(): Boolean {
        if(etTitle.text.isBlank()){
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etDescription.text.isBlank()) {
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
            return false
        }
        val firstIngredient = ingredientsContainer.getChildAt(0) as? EditText
        if (firstIngredient == null || firstIngredient.text.isBlank()) {
            Toast.makeText(this, "Please enter at least one ingredient", Toast.LENGTH_SHORT).show()
            return false
        }
        for (i in 0 until ingredientsContainer.childCount) {
            val field = ingredientsContainer.getChildAt(i) as? EditText
            if (field != null && field.text.isBlank()) {
                Toast.makeText(this, "Please fill in all ingredient fields", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        val firstStep = instructionsContainer.getChildAt(0) as? EditText
        if (firstStep == null || firstStep.text.isBlank()) {
            Toast.makeText(this, "Please enter at least one instruction step", Toast.LENGTH_SHORT).show()
            return false
        }
        for (i in 0 until instructionsContainer.childCount) {
            val field = instructionsContainer.getChildAt(i) as? EditText
            if (field != null && field.text.isBlank()) {
                Toast.makeText(this, "Please fill in all instruction steps", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun buildRecipe(): HashMap<String, Any> {
        val ingredients = List(ingredientsContainer.childCount) { i ->
            val field = ingredientsContainer.getChildAt(i) as EditText
            field.text.toString().trim()
        }
        val instructions = List(instructionsContainer.childCount) {i ->
            val field = instructionsContainer.getChildAt(i) as EditText
            field.text.toString().trim()
        }
        val recipe = hashMapOf<String, Any>(
            "description" to etDescription.text.toString().trim(),
            "ingredients" to ingredients,
            "instructions" to instructions
        )
        return recipe
    }

    private fun submitRecipe(map: HashMap<String, Any>) {
        val title = etTitle.text.toString().trim()
        MainActivity.mealio!!.save_recipe(title, map, "my_recipes")
        Log.w("MainActivity", "save_recipe ran")
        val keywords = title.lowercase().split(" ")
        for (keyword in keywords) {
            if (keyword.isNotBlank()) {
                MainActivity.mealio!!.save_keyword(keyword, title)
            }
        }
        editor.remove("TITLE").remove("DESCRIPTION").remove("INGREDIENTS").remove("INSTRUCTIONS")
            .commit()
    }
}