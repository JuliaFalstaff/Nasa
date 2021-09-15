package com.example.nasaapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentSettingsBinding
import com.example.nasaapp.view.MainActivity
import com.google.android.material.radiobutton.MaterialRadioButton

class FavouriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteFragment()
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




}


