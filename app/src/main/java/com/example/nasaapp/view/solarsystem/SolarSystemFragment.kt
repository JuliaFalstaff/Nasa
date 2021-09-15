package com.example.nasaapp.view.solarsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasaapp.R
import com.example.nasaapp.api.viewpager.ViewPagerAdapter
import com.example.nasaapp.databinding.FragmentSolarSystemBinding
import com.example.nasaapp.view.favourite.FavouriteFragment
import com.example.nasaapp.view.picture.PODFragment
import com.example.nasaapp.view.settings.SettingsFragment

class SolarSystemFragment : Fragment() {

    private var _binding: FragmentSolarSystemBinding? = null
    val binding: FragmentSolarSystemBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSolarSystemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        initTabIcons()
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomApiNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    openFragment(PODFragment())
                    true
                }
                R.id.action_fav -> {
                    Toast.makeText(context, R.string.favourite, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_settings -> {
                    openFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun initTabIcons() = with(binding) {
        tabLayout.getTabAt(0)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tablayout_earth,null)
        tabLayout.getTabAt(1)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tablayout_mars,null)
        tabLayout.getTabAt(2)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tablayout_moon,null)
    }


    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }




    companion object {
        fun newInstance() = SolarSystemFragment()
    }

}