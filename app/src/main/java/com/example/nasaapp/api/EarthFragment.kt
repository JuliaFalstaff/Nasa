package com.example.nasaapp.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasaapp.BuildConfig
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentEarthBinding
import com.example.nasaapp.model.AppState
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.viewmodel.EarthViewModel

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private lateinit var dayFromURL: String
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
        viewModel.getEarthEpicPictureFromServerByDate()
    }

    private fun renderData(data: AppState?) {
        when (data) {
            is AppState.Error -> {
                binding.earthFragment.showSnackBar(getString(R.string.error_appstate),
                        getString(R.string.reload_appstate), { viewModel.getEarthEpicPictureFromServerByDate() })
            }
            is AppState.Loading -> {
                binding.earthImageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
            is AppState.SuccessEarthEpic -> {
                setData(data)
            }
        }
    }

    private fun setData(data: AppState.SuccessEarthEpic) = with(binding) {
        val image = data.serverResponseData.first().image
        dayFromURL = getDateFromURL(data.serverResponseData.first().date)
        val currentDayFromUrl = dayFromURL

        if (image.isEmpty()) {
            Toast.makeText(context, getString(R.string.error_url_empty), Toast.LENGTH_LONG).show()
        } else {
            earthImageView.load(BASE_URL + dayFromURL + IMAGE_FORMAT + image + SUFFIX_PNG + BuildConfig.NASA_API_KEY) {
                placeholder(R.drawable.progress_animation) // этот
                error(R.drawable.ic_load_error_vector)
            }
        }
    }

    private fun getDateFromURL(date: String): String {
        var serverDate = date.replace("-","/",true)
        serverDate = serverDate.dropLast(9)
        return serverDate
    }

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/EPIC/archive/natural/"
        private const val IMAGE_FORMAT = "/png/"
        private const val SUFFIX_PNG = ".png?api_key="
    }
}
