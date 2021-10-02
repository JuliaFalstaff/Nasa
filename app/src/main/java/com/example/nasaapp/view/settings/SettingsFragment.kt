package com.example.nasaapp.view.settings

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentSettingsStartBinding

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
        private const val THEME_SHARED_PREFERENCE = "THEME"
        private const val DefaultTheme = 0
        private const val GalaxyTheme = 1
        private const val MarsTheme = 2
        private const val MoonTheme = 3
    }

    var show = false
    var _binding: FragmentSettingsStartBinding? = null
    val binding: FragmentSettingsStartBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsAnimation()
        showSettingTitle()
        initCheckedButtonTheme()
        chooseTheme()
    }

    private fun showSettingTitle() {
        val spannable = SpannableStringBuilder(getString(R.string.settings_header))
        spannable.setSpan(StrikethroughSpan(), 7, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.BLACK), 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.titleSettings.text = spannable
    }

    private fun settingsAnimation() = with(binding) {
        buttonChooseTheme.setOnClickListener {
            show = !show
            val constraintSet = ConstraintSet()
            val transition = ChangeBounds()
            if (show) {
                constraintSet.clone(context, R.layout.fragment_settings_end)
                transition.interpolator = AnticipateOvershootInterpolator(1.0f)
                transition.duration = 1000
            } else {
                constraintSet.clone(context, R.layout.fragment_settings_start)
                transition.interpolator = AnticipateOvershootInterpolator(1.0f)
                transition.duration = 1000
            }
            android.transition.TransitionManager.beginDelayedTransition(constraintContainer, transition)
            constraintSet.applyTo(binding.constraintContainer)
        }
    }

    private fun chooseTheme() = with(binding) {
        radioButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonGalaxyTheme -> {
                    radioButtonGalaxyTheme.isChecked = true
                    saveSharedPreferences(GalaxyTheme)
                }
                R.id.radioButtonMarsTheme -> {
                    radioButtonMarsTheme.isChecked = true
                    saveSharedPreferences(MarsTheme)
                }
                R.id.radioButtonMoonTheme -> {
                    radioButtonMoonTheme.isChecked = true
                    saveSharedPreferences(MoonTheme)
                }
                R.id.radioButtonDefaultTheme -> {
                    radioButtonDefaultTheme.isChecked = true
                    saveSharedPreferences(DefaultTheme)
                }
            }
        }
    }

    private fun saveSharedPreferences(code: Int) {
        var sharedPref = activity?.let {
            it.getSharedPreferences(THEME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                    .edit()
                    .putInt(THEME_SHARED_PREFERENCE, code)
                    .apply()
            it.supportFragmentManager.popBackStack()
            it.recreate()
        }
    }

    private fun initCheckedButtonTheme() {
        when (getCurrentTheme()) {
            GalaxyTheme -> binding.radioButtonGroup.check(R.id.radioButtonGalaxyTheme)
            MarsTheme -> binding.radioButtonGroup.check(R.id.radioButtonMarsTheme)
            MoonTheme -> binding.radioButtonGroup.check(R.id.radioButtonMoonTheme)
            DefaultTheme -> binding.radioButtonGroup.check(R.id.radioButtonDefaultTheme)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getCurrentTheme(): Int? {
        var sharedPref = activity?.let {
            it.getSharedPreferences(THEME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        }
        return sharedPref?.getInt(THEME_SHARED_PREFERENCE, -1)
    }
}


