package com.example.mealio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay

class HomePage : AppCompatActivity() {
    private lateinit var adView : AdView
    private lateinit var settingsButton : ImageView
    private lateinit var findButton : MaterialButton
    private lateinit var createButton : MaterialButton
    private lateinit var favedButton : MaterialButton
    private lateinit var createdButton : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        // AdView stuff
        adView = AdView(this)
        var adSize : AdSize = AdSize(AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT)
        adView.setAdSize(adSize)
        var adUnitId : String = "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId

        var builder : AdRequest.Builder = AdRequest.Builder()
        var request : AdRequest = builder.build()

        var adLinearLayout : LinearLayout = findViewById<LinearLayout>(R.id.ad_layout)
        adLinearLayout.addView(adView)

        adView.loadAd(request)

        // Change welcome message
        val welcomeTV : TextView = findViewById<TextView>(R.id.welcome_bar)
        welcomeTV.text = "Loading..."
        Log.w("MainActivity", "probably loaded")

        Log.w("MainActivity","user_info: ${MainActivity.mealio!!.user_info}")

        val username : String = MainActivity.mealio!!.user_info!!["username"] as String
        welcomeTV.text = "Welcome back, $username!\nWhat's on the menu today?"

        // Button on-click listeners
        findButton = findViewById<MaterialButton>(R.id.lookup)
        createButton = findViewById<MaterialButton>(R.id.create)
        favedButton = findViewById<MaterialButton>(R.id.favorite)
        createdButton = findViewById<MaterialButton>(R.id.mine)

        createButton.setOnClickListener { view ->
            val intent : Intent = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intent)
        }

    }

//    inner class onClick : View.OnClickListener {
//        override fun onClick(p0: View?) {
//            if(p0 != null) {
//
//            }
//        }
//
//    }


    // Ad lifecycle
    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }
}