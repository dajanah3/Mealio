package com.example.mealio

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class WelcomeView : Fragment(R.layout.activity_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<Button>(R.id.login)
        val createButton = view.findViewById<Button>(R.id.create)

        loginButton.setOnClickListener {
            val intent = Intent(activity, LoginView::class.java)
            startActivity(intent)
        }

        createButton.setOnClickListener {
            val intent = Intent(activity, CreateAccountView::class.java)
            startActivity(intent)
        }
    }
}