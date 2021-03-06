package com.example.nasaapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapp.R
import com.example.nasaapp.databinding.ActivityMainBinding
import com.example.nasaapp.view.picture.PODFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val themeId: Int by lazy {
        getSharedPreferences(THEME_SHARED_PREFERENCE, MODE_PRIVATE).getInt(THEME_SHARED_PREFERENCE, DefaultTheme)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(chooseTheme(themeId))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PODFragment.newInstance())
                    .commitNow()
        }
    }

    private fun chooseTheme(theme: Int): Int {
        return when (theme) {
            GalaxyTheme -> R.style.GalaxyAppTheme
            MarsTheme -> R.style.MarsAppTheme
            MoonTheme -> R.style.MoonAppTheme
            DefaultTheme -> R.style.NasaApp
            else -> R.style.NasaApp
        }
    }

    companion object {
        private const val THEME_SHARED_PREFERENCE = "THEME"
        private const val DefaultTheme = 0
        private const val GalaxyTheme = 1
        private const val MarsTheme = 2
        private const val MoonTheme = 3
    }
}