package com.example.nasaapp.view.picture

import android.content.Intent
import android.net.Uri
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
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentPodBinding
import com.example.nasaapp.model.PODData
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.viewmodel.PODViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PODFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
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
        setBottomSheetBehavior(binding.includeBottomSheetLayout.bottomSheetContainer)
    }

    private fun openWikiSearch() = with(binding) {
        inputTextLayout.setEndIconOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
            }
            startActivity(i)
        }
    }

    private fun renderData(data: PODData?)  {
        when(data) {
            is PODData.Error -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                binding.main.showSnackBar(getString(R.string.error_appstate),
                    getString(R.string.reload_appstate), {viewModel.getPODFromServer()})
            }
            is PODData.Loading -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is PODData.Success -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                setData(data)
            }
        }
    }

    private fun setData(data: PODData.Success) = with(binding) {
        val url = data.serverResponseData.hdurl
        if (url.isNullOrEmpty()) {
            Toast.makeText(context, getString(R.string.error_url_empty), Toast.LENGTH_LONG).show()
        } else {
            customImageView.load(url) {
                error(R.drawable.ic_load_error_vector)
            }
        }
        includeBottomSheetLayout.bottomSheetDescriptionHeader.text = data.serverResponseData.title.toString()
        includeBottomSheetLayout.bottomSheetDescription.text = data.serverResponseData.explanation.toString()
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PODFragment()
    }
}