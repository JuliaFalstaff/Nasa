package com.example.nasaapp.view.favourite

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.nasaapp.databinding.FragmentFavouriteBinding
import com.example.nasaapp.databinding.FragmentSettingsBinding
import com.google.android.material.transition.MaterialArcMotion

class FavouriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    var isRightAnimation = false
    var _binding: FragmentFavouriteBinding? = null
    val binding: FragmentFavouriteBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateButton()
        setListAnimator()
    }

    private fun setListAnimator() {
        binding.scrollViewFavourite.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.favouriteContainer.isSelected = binding.scrollViewFavourite.canScrollVertically(-1)
        }
    }

    private fun animateButton() = with(binding) {
        buttonFavourite.setOnClickListener {
            isRightAnimation = !isRightAnimation
            val changeBounds = ChangeBounds()
            changeBounds.setPathMotion(MaterialArcMotion())
            changeBounds.duration = 2000
            TransitionManager.beginDelayedTransition(favouriteContainer, changeBounds)

            val params = buttonFavourite.layoutParams as FrameLayout.LayoutParams
            if (isRightAnimation) {
                params.gravity = Gravity.END or Gravity.BOTTOM
            } else {
                params.gravity = Gravity.TOP or Gravity.START
            }
            buttonFavourite.layoutParams = params
        }
    }
}


