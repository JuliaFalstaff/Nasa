package com.example.nasaapp.view.explodegame

import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentExplosionGameRecyclerBinding

class ExplosionGameFragment : Fragment() {

    companion object {
        fun newInstance() = ExplosionGameFragment()
    }

    var _binding: FragmentExplosionGameRecyclerBinding? = null
    val binding: FragmentExplosionGameRecyclerBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExplosionGameRecyclerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewExplosion.adapter = Adapter()
    }

    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true)
        explode.duration = 4000
        val fade = Fade().addTarget(clickedView)
        fade.duration = 4000
        val setTransition = TransitionSet()
                .addTransition(explode)
                .addTransition(fade)
                .addListener(object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) {
                        showDialog()

                    }
                })

        TransitionManager.beginDelayedTransition(binding.recyclerViewExplosion, setTransition)
        binding.recyclerViewExplosion.adapter = null
    }

    private fun showDialog() {
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_apply, null)
                .setIcon(R.drawable.ic_earth_new)
                .show()
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.fragment_explosion_game_list_item,
                            parent,
                            false
                    ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}