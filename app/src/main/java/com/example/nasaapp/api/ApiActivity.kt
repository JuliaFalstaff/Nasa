package com.example.nasaapp.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapp.R
import com.example.nasaapp.databinding.ActivityApiBinding
import com.example.nasaapp.view.FavouriteFragment
import com.example.nasaapp.view.picture.PODFragment
import com.example.nasaapp.view.settings.SettingsFragment

class ApiActivity : AppCompatActivity() {

    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        initTabIcons()

        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomApiNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PODFragment()).commit()
                    true
                }
                R.id.action_fav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavouriteFragment()).commit()
                    true
                }
                R.id.action_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun initTabIcons() = with(binding) {
        binding.tabLayout.getTabAt(0)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tablayout_earth,null)
        binding.tabLayout.getTabAt(1)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tablayout_mars,null)
        binding.tabLayout.getTabAt(2)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tablayout_moon,null)
    }

}