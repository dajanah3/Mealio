package com.example.mealio

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity() {
    private lateinit var keywordSearch : AutoCompleteTextView
    private lateinit var backBT : ImageButton
    val db : FirebaseFirestore = MainActivity.mealio!!.get_db()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        backBT = findViewById<ImageButton>(R.id.back)
        keywordSearch = findViewById<AutoCompleteTextView>(R.id.keyword_search)

        backBT.setOnClickListener {
            finish()
        }
        // wow

        db.collection("keywords").get()
            .addOnSuccessListener { collection ->
                val keywords = mutableListOf<String>()
                for (keyword in collection) {
                    keywords.add(keyword.id)
                }
                getAutoComplete(keywords.toTypedArray())
            }
    }

    fun getAutoComplete(s : Array<String>) {
        var adapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, s)
        keywordSearch.setAdapter(adapter)
        keywordSearch.threshold = 0
        keywordSearch.dropDownWidth = resources.displayMetrics.widthPixels
        keywordSearch.setOnItemClickListener { parent, view, position, id ->
            var selectedItem : String = parent.getItemAtPosition(position).toString()

            db.collection("keywords").document(selectedItem).get()
                .addOnSuccessListener { keyword ->
                    if (keyword.exists()) {

                        val keys = (keyword.get("recipes") as ArrayList<String>)
                        val intent : Intent = Intent(this, DisplayAllActivity::class.java)
                        intent.putStringArrayListExtra("recipe_keys", keys)
                        startActivity(intent)

                    }


                }

        }
    }

}