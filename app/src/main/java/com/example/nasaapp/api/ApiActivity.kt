package com.example.nasaapp.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapp.R
import com.example.nasaapp.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        initTabIcons()
    }

    private fun initTabIcons() = with(binding) {
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_system)  //TODO заменить на луну
    }
}