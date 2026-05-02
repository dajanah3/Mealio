package com.example.mealio

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mealio.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginView : Fragment(R.layout.activity_login) {

    private var binding: ActivityLoginBinding? = null
    private val auth = FirebaseAuth.getInstance()
    private lateinit var mealio : Mealio

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.login?.setOnClickListener {
            val email = binding?.email?.text.toString()
            val password = binding?.password?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val appState = Mealio(requireContext(), user)
                            Toast.makeText(context, "Welcome back to Mealio!", Toast.LENGTH_SHORT).show()
                            // THEN IG GOTTA SWITCH TO MAIN SCREEN VIEW HERE (CHARLES)
                        }
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}