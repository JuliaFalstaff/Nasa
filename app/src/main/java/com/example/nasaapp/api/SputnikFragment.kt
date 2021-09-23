package com.example.nasaapp.api

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentSputnikBinding
import com.example.nasaapp.model.AppState
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.viewmodel.SputnikViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SputnikFragment : Fragment() {

    private var isExpanded = false
    private var _binding: FragmentSputnikBinding? = null
    val binding: FragmentSputnikBinding
        get() {
            return _binding!!
        }

    private val viewModel: SputnikViewModel by lazy {
        ViewModelProvider(this).get(SputnikViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSputnikBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        getData()
        expandSputnikPicture()
    }

    private fun expandSputnikPicture() {
        binding.sputnikImageView.setOnClickListener {
            isExpanded = !isExpanded
            val set = TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())

            TransitionManager.beginDelayedTransition(binding.sputnikFragment, set)

            binding.sputnikImageView.scaleType = if (isExpanded) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }
        }
    }

    private fun getData() {
        viewModel.getLandscapePictureFromServer(
                getCurrentDate(),
                LON_HUSTON,
                LAT_HUSTON,
                DIM
        )
    }

    private fun getCurrentDate(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val today = LocalDateTime.now().minusDays(0)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return today.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, 0)
            return s.format(cal.time)
        }
    }

    private fun renderData(data: AppState?) {
        when (data) {
            is AppState.Error -> {
                binding.sputnikFragment.showSnackBar(getString(R.string.error_appstate),
                        getString(R.string.reload_appstate), { getData() })
            }
            is AppState.Loading -> {
                binding.sputnikImageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
            is AppState.SuccessSputnik -> {
                setData(data)
            }
        }
    }

    private fun setData(data: AppState.SuccessSputnik) = with(binding) {
        val image = data.serverResponseData.url.toString()

        if (image.isEmpty()) {
            Toast.makeText(context, getString(R.string.error_url_empty), Toast.LENGTH_LONG).show()
        } else {
            sputnikImageView.load(image) {
                placeholder(R.drawable.progress_animation) // этот
                error(R.drawable.ic_load_error_vector)
            }
        }
    }

    companion object {
        private const val LON_HUSTON = -95.33F
        private const val LAT_HUSTON = 29.78F
        private const val DIM = 0.10F
    }
}