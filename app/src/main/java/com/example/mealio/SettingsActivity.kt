package com.example.mealio

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity: AppCompatActivity() {
    private lateinit var back : ImageButton
    private lateinit var switchBT : SwitchMaterial
    private lateinit var signOutBT : MaterialButton
    private lateinit var editor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // SharedPreferences
        val sp = this.getSharedPreferences(this.packageName + "_preferences", Context.MODE_PRIVATE)
        editor = sp.edit()

        back = findViewById<ImageButton>(R.id.back)
        signOutBT = findViewById<MaterialButton>(R.id.logout)
        switchBT = findViewById<SwitchMaterial>(R.id.material_switch)

        var remember = sp.getBoolean("REMEMBER", false)
        if(remember) {
            switchBT.isChecked = true
        } else {
            switchBT.isChecked = false
        }

        back.setOnClickListener {
            finish()
        }

        // Remember unsaved recipes
        switchBT.setOnClickListener {
            if(switchBT.isChecked) {
                editor.putBoolean("REMEMBER", true)
            } else {
                editor.putBoolean("REMEMBER", false)
            }
            editor.commit()
        }

        // Go back to MainActivity
        signOutBT.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }


    }



}