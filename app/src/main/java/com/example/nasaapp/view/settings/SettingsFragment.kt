package com.example.nasaapp.view.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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

    private var isCheckedButton: Boolean = false
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

//        activity?.let {
//            it.getPreferences(Context.MODE_PRIVATE)?.getInt(THEME_SHARED_PREFERENCE, DefaultTheme)?.let { button ->
//                binding.radioButtonGroup.check(button)
//            }
//        }

        chooseTheme()

    }







    private fun chooseAppTheme() {
        binding.buttonApplyTheme.setOnClickListener {
            chooseTheme()
//            activity?.recreate()
        }
    }

    private fun chooseTheme() = with(binding) {
        radioButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonGalaxyTheme -> {
                    isCheckedButton = true
                    saveSharedPreferences(GalaxyTheme)
//                    activity?.recreate()
                }
                R.id.radioButtonMarsTheme -> {
                    isCheckedButton = true
                    saveSharedPreferences(MarsTheme)
//                    activity?.recreate()
                }
                R.id.radioButtonMoonTheme -> {
                    isCheckedButton = true
                    saveSharedPreferences(MoonTheme)
//                    activity?.recreate()
                }
                R.id.radioButtonDefaultTheme -> {
                    isCheckedButton = true
                    saveSharedPreferences(DefaultTheme)
//                    activity?.recreate()
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
            it.recreate()
    }

}


override fun onDestroy() {
    super.onDestroy()
    _binding = null
}
}


