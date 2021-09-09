package com.example.nasaapp.view.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nasaapp.R
import com.example.nasaapp.databinding.BottomNavigationLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
    }

    fun setNavigation() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.app_bar_fav -> Toast.makeText(context, getString(R.string.favourite), Toast.LENGTH_SHORT).show()
                R.id.app_bar_settings -> Toast.makeText(context, getString(R.string.settings), Toast.LENGTH_SHORT).show()
                R.id.app_bar_earth_epic -> Toast.makeText(context, getString(R.string.earth_epic), Toast.LENGTH_SHORT).show()
                R.id.app_bar_mars -> Toast.makeText(context, getString(R.string.mars), Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = BottomNavigationDrawerFragment()
    }


}