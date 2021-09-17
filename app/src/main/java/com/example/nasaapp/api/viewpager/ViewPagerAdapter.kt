package com.example.nasaapp.api.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasaapp.api.EarthFragment
import com.example.nasaapp.api.MarsFragment
import com.example.nasaapp.api.SputnikFragment

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SputnikFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FIRST_POSITION -> fragments[EARTH_FRAGMENT]
            SECOND_POSITION -> fragments[MARS_FRAGMENT]
            THIRD_POSITION -> fragments[SPUTNIK_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): String? {
        return null
    }

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val SPUTNIK_FRAGMENT = 2
        private const val FIRST_POSITION = 0
        private const val SECOND_POSITION = 1
        private const val THIRD_POSITION = 2
    }
}