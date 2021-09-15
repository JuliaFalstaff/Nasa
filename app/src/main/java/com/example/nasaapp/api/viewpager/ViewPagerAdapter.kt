package com.example.nasaapp.api.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasaapp.api.EarthFragment
import com.example.nasaapp.api.MarsFragment
import com.example.nasaapp.api.MoonFragment

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), MoonFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[MOON_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): String {
        return when (position) {
            0 -> "Earth"
            1 -> "Mars"
            2 -> "Moon"
            else -> "Earth"
        }
    }

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val MOON_FRAGMENT = 2
    }
}