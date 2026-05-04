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
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, LoginView())
                .addToBackStack(null)
                .commit()
        }

        createButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, CreateAccountView())
                .addToBackStack(null)
                .commit()
        }
    }
}