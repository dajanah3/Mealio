package com.example.mealio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(androidx.fragment.R.id.fragment_container_view_tag, WelcomeView())
                .commit()
        }
    }

    fun switch(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(androidx.fragment.R.id.fragment_container_view_tag, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object{
        var mealio: Mealio? = null
    }
}