package com.example.nasaapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaapp.databinding.FragmentAboutBinding

class AboutAppFragment: Fragment() {

    companion object {
        fun newInstance() = AboutAppFragment()
    }

    var _binding: FragmentAboutBinding? = null
    val binding: FragmentAboutBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater)
        return binding.root
    }
}