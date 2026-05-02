package com.example.mealio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class WelcomeView : Fragment(R.layout.activity_welcome) {
    private var binding : FragmentWelcomeBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeBinding.bind(view)

        binding?.apply {
            login.setOnClickListener{ findNavController().navigate(R.id.loginAction) }
            create.setOnClickListener{ findNavController().navigate(R.id.createAction) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}