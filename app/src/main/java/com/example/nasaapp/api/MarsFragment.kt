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
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentMarsBinding
import com.example.nasaapp.model.AppState
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.viewmodel.MarsViewModel

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    val binding: FragmentMarsBinding
        get() {
            return _binding!!
        }

    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMarsPictureFromServer()
    }


    private fun renderData(data: AppState?) {
        when (data) {
            is AppState.Error -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                binding.marsFragment.showSnackBar(getString(R.string.error_appstate),
                        getString(R.string.reload_appstate), { viewModel.getMarsPictureFromServer() })
            }
            is AppState.Loading -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessMars -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                setData(data)
            }
        }
    }

    private fun setData(data: AppState.SuccessMars) = with(binding) {
        val url = data.serverResponseData.photos.first().imgSrc
        if (url.isNullOrEmpty()) {
            Toast.makeText(context, getString(R.string.error_url_empty), Toast.LENGTH_LONG).show()
        } else {
            marsImageView.load(url) {
                error(R.drawable.ic_load_error_vector)
            }
        }
    }
}