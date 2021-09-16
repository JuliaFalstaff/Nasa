package com.example.nasaapp.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasaapp.BuildConfig
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentEarthBinding
import com.example.nasaapp.databinding.FragmentPodBinding
import com.example.nasaapp.model.AppState
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.viewmodel.EarthViewModel
import com.example.nasaapp.viewmodel.PODViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    val binding: FragmentEarthBinding
        get() {
            return _binding!!
        }

    private val viewModel: EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getEarthEpicPictureFromServer()

    }

    private fun renderData(data: AppState?) {
        when (data) {
            is AppState.Error -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                binding.earthFragment.showSnackBar(getString(R.string.error_appstate),
                        getString(R.string.reload_appstate), { viewModel.getEarthEpicPictureFromServer() })
            }
            is AppState.Loading -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessEarthEpic -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                setData(data)
            }
        }
    }

    private fun setData(data: AppState.SuccessEarthEpic) = with(binding) {
        val image = data.serverResponseData.first().image

        if (image.isEmpty()) {
            Toast.makeText(context, getString(R.string.error_url_empty), Toast.LENGTH_LONG).show()
        } else {
            earthImageView.load(BASE_URL + image + SUFFIX_PNG + BuildConfig.NASA_API_KEY) {
                error(R.drawable.ic_load_error_vector)
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/EPIC/archive/natural/2021/09/15/png/"
        private const val SUFFIX_PNG = ".png?api_key="

//
//
//
//        https://api.nasa.gov/EPIC/archive/natural/2019/05/30/png/epic_1b_20190530011359.png?api_key=DEMO_KEY

    }
}
