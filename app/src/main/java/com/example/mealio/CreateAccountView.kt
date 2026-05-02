package com.example.mealio
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mealio.databinding.ActivityCreateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateAccountView : Fragment(R.layout.activity_create) {

    private var binding : ActivityCreateBinding? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var mealio : Mealio

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivityCreateBinding.bind(view)

        binding?.createAccount?.setOnClickListener {
            val email = binding?.email?.text.toString()
            val password = binding?.password?.text.toString()
            val username = binding?.username?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        addUser(username, email)
                    } else {
                        Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun addUser(username: String, email: String) {
        val userId = auth.currentUser?.uid ?: return
        val userMap = hashMapOf(
            "username" to username,
            "email" to email,
            "password" to binding?.password?.text.toString() // pass plain text rn, change later
        )

        db.collection("users").document(userId).set(userMap)
            .addOnSuccessListener {
                val user = auth.currentUser
                if (user != null) {
                    val appState = Mealio(requireContext(), user)
                    Toast.makeText(context, "Welcome to Mealio!", Toast.LENGTH_SHORT).show()
                    // THEN IG GOTTA SWITCH TO MAIN SCREEN VIEW HERE (CHARLES)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}