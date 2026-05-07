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
import com.google.android.gms.ads.MobileAds
import com.google.android.material.button.MaterialButton
import kotlin.concurrent.thread

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

        var adLinearLayout : LinearLayout = findViewById<LinearLayout>(R.id.ad_layout)
        adLinearLayout.addView(adView)

        thread(name = "MobileAdsInit") {
            MobileAds.initialize(this) {
                runOnUiThread { adView.loadAd(AdRequest.Builder().build()) }
            }
        }

        // Change welcome message
        val welcomeTV : TextView = findViewById<TextView>(R.id.welcome_bar)
        val welcomeSubtitle : TextView = findViewById<TextView>(R.id.welcome_subtitle)

        if (MainActivity.mealio?.user_info == null) {
            // Login error?
            Log.w("MainActivity", "Login Error")
            finish()
            return
        }

        // Log user_info just cuz
        Log.w("MainActivity","user_info: ${MainActivity.mealio!!.user_info}")

        val username : String = MainActivity.mealio!!.user_info!!["username"] as String

        welcomeTV.text = "Welcome back, $username"
        welcomeSubtitle.text = "What's on the menu today?"

        // Assign components
        settingsButton = findViewById<ImageView>(R.id.settings)
        findButton = findViewById<MaterialButton>(R.id.lookup)
        createButton = findViewById<MaterialButton>(R.id.create)
        favedButton = findViewById<MaterialButton>(R.id.favorite)
        createdButton = findViewById<MaterialButton>(R.id.mine)

        // Settings button (dark mode, sign out?)
        settingsButton.setOnClickListener {
            val intent : Intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Recipe lookup activity
        findButton.setOnClickListener {
            val intent : Intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // Recipe creation activity
        createButton.setOnClickListener { view ->
            val intent : Intent = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intent)
        }

        createdButton.setOnClickListener {
            val keys = MainActivity.mealio!!.get_user_recipes()
            val intent : Intent = Intent(this, DisplayAllActivity::class.java)
            intent.putStringArrayListExtra("recipe_keys", keys)
            startActivity(intent)
        }

        favedButton.setOnClickListener {
            val keys = MainActivity.mealio!!.get_fav_recipes()
            val intent : Intent = Intent(this, DisplayAllActivity::class.java)
            intent.putStringArrayListExtra("recipe_keys", keys)
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