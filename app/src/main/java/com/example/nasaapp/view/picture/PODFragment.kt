package com.example.nasaapp.view.picture

import android.content.Intent
import android.net.Uri
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
import com.example.nasaapp.databinding.FragmentPodBinding
import com.example.nasaapp.model.PODData
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.viewmodel.PODViewModel

class PODFragment : Fragment() {

    private var _binding: FragmentPodBinding? = null
    val binding: FragmentPodBinding
    get() {
        return _binding!!
    }

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPodBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getPODFromServer()
        openWikiSearch()
    }

    private fun openWikiSearch() = with(binding) {
        inputLayout.setEndIconOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
            }
            startActivity(i)
        }
    }

    private fun renderData(data: PODData?)  {
        when(data) {
            is PODData.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.main.showSnackBar(getString(R.string.error_appstate),
                    getString(R.string.reload_appstate), {viewModel.getPODFromServer()})
            }
            is PODData.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is PODData.Success -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                val url = data.serverResponseData.hdurl
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, getString(R.string.error_url_empty), Toast.LENGTH_LONG).show()
                } else {
                    binding.customImageView.load(url) {
                        error(R.drawable.ic_load_error_vector)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PODFragment()
    }
}