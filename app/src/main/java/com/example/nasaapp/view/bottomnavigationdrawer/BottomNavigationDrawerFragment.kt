package com.example.nasaapp.view.bottomnavigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasaapp.R
import com.example.nasaapp.databinding.BottomNavigationLayoutBinding
import com.example.nasaapp.view.about.AboutAppFragment
import com.example.nasaapp.view.explodegame.ExplosionGameFragment
import com.example.nasaapp.view.notes.NoteFragment
import com.example.nasaapp.view.settings.SettingsFragment
import com.example.nasaapp.view.solarsystem.SolarSystemFragment
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

    private fun setNavigation() {
        binding.navigationView.itemIconTintList = null
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_fav -> Toast.makeText(context, getString(R.string.favourite), Toast.LENGTH_SHORT).show()
                R.id.action_settings -> openFragment(SettingsFragment())
                R.id.action_explosion_game -> openFragment(ExplosionGameFragment())
                R.id.action_about -> openFragment(AboutAppFragment())
                R.id.action_note -> openFragment(NoteFragment())
            }
            dismiss()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack("")
                    .commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance() = BottomNavigationDrawerFragment()
    }
}