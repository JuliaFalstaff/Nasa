package com.example.nasaapp.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
        private const val THEME_SHARED_PREFERENCE = "THEME"
        private const val DefaultTheme = 0
        private const val GalaxyTheme = 1
        private const val MarsTheme = 2
        private const val MoonTheme = 3
    }


    var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = binding.radioButtonGroup.checkedRadioButtonId
        binding.radioButtonGroup.check(btn)
        chooseTheme()
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
        val sharedPref = activity?.let {
            it.getSharedPreferences(THEME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                    .edit()
                    .putInt(THEME_SHARED_PREFERENCE, code)
                    .apply()
            it.supportFragmentManager.popBackStack()
            it.recreate()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


