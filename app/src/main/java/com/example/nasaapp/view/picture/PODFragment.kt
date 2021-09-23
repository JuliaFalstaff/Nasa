package com.example.nasaapp.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentPodStartBinding
import com.example.nasaapp.model.AppState
import com.example.nasaapp.utils.showSnackBar
import com.example.nasaapp.view.MainActivity
import com.example.nasaapp.view.bottomnavigationdrawer.BottomNavigationDrawerFragment
import com.example.nasaapp.view.explodegame.ExplosionGameFragment
import com.example.nasaapp.view.favourite.FavouriteFragment
import com.example.nasaapp.view.settings.SettingsFragment
import com.example.nasaapp.view.solarsystem.SolarSystemFragment
import com.example.nasaapp.viewmodel.PODViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class PODFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: FragmentPodStartBinding? = null
    val binding: FragmentPodStartBinding
        get() {
            return _binding!!
        }

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPodStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        getData()
        openWikiSearch()
        setBottomSheetBehavior(binding.includeBottomSheetLayout.bottomSheetContainer)
        setBottomAppBar()
    }

    private fun getData() {
        initChips()
    }

    private fun initChips() = with(binding) {
        chipsDayGroup.chipsGroup.check(R.id.chipToday)
        viewModel.getPODFromServer(getDay(0))

        chipsDayGroup.chipsGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipToday -> {
                    chipsDayGroup.chipsGroup.check(R.id.chipToday)
                    viewModel.getPODFromServer(getDay(TODAY))
                }
                R.id.chipYesterday -> {
                    chipsDayGroup.chipsGroup.check(R.id.chipYesterday)
                    viewModel.getPODFromServer(getDay(YESTERDAY))
                }
                R.id.chipDayBeforeYesterday -> {
                    chipsDayGroup.chipsGroup.check(R.id.chipDayBeforeYesterday)
                    viewModel.getPODFromServer(getDay(BEFORE_YESTERDAY))
                }
                else -> viewModel.getPODFromServer(getDay(TODAY))
            }
        }
    }


    private fun openWikiSearch() = with(binding) {
        inputTextLayout.setEndIconOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_URL${inputEditText.text.toString()}")
            }
            startActivity(i)
        }
    }

    private fun renderData(data: AppState?) {
        when (data) {
            is AppState.Error -> {
                binding.main.showSnackBar(getString(R.string.error_appstate),
                        getString(R.string.reload_appstate), { getData() })
            }
            is AppState.Loading -> {
                binding.customImageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
            is AppState.Success -> {
                setData(data)
            }
        }
    }

    private fun setData(data: AppState.Success) = with(binding) {
        videoOfTheDay.visibility = View.GONE
        val url = data.serverResponseData.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.serverResponseData.url
            customImageView.setImageResource(R.drawable.ic_no_photo_vector)
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            videoOfTheDay.visibility = View.GONE
            customImageView.load(url) {
                placeholder(R.drawable.progress_animation)
                error(R.drawable.ic_load_error_vector)
            }
        }
        includeBottomSheetLayout.bottomSheetDescriptionHeader.text = data.serverResponseData.title.toString()
        includeBottomSheetLayout.bottomSheetDescription.text = data.serverResponseData.explanation.toString()
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = getString(R.string.video) + videoUrl.toString()
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> openFragment(FavouriteFragment())
            R.id.app_bar_settings -> openFragment(SettingsFragment())
            R.id.app_bar_solar -> openFragment(SolarSystemFragment())
            R.id.app_bar_explosion_game -> openFragment(ExplosionGameFragment())
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance().show(requireActivity().supportFragmentManager, "TAG_DRAWER")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("")
                    .commitAllowingStateLoss()
        }
    }

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getDay(minusDay: Int): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(minusDay.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, (-minusDay))
            return s.format(cal.time)
        }
    }

    companion object {
        fun newInstance() = PODFragment()
        private const val WIKI_URL = "https://en.wikipedia.org/wiki/"
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2

    }
}