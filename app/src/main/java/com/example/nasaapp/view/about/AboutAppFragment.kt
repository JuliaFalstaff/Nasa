package com.example.nasaapp.view.about

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.nasaapp.databinding.FragmentAboutBinding


class AboutAppFragment: Fragment() {

    private var textIsVisible = false
    private var logoIsVisible = false

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAbout.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.aboutContainer, Slide(Gravity.END))
            textIsVisible = !textIsVisible
            binding.nasaDescription.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }

        binding.nasaTitle.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.aboutContainer)
            logoIsVisible = !logoIsVisible
            binding.nasaLogoImageView.visibility = if (logoIsVisible) View.VISIBLE else View.GONE
        }
    }
}