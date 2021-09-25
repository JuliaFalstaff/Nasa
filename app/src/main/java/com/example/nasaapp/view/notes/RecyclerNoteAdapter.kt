package com.example.nasaapp.view.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.databinding.FragmentNotesRecyclerEarthItemBinding
import com.example.nasaapp.databinding.FragmentNotesRecyclerMarsItemBinding
import com.example.nasaapp.model.data.DataNote

class RecyclerNoteAdapter(
        private var noteData: MutableList<DataNote>,
        private var onListItemClickListener: OnListItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding: FragmentNotesRecyclerEarthItemBinding = FragmentNotesRecyclerEarthItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                EarthViewHolder(binding.root)
            }
            TYPE_MARS -> {
                val binding: FragmentNotesRecyclerMarsItemBinding = FragmentNotesRecyclerMarsItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                MarsViewHolder(binding.root)
            }
            else -> {
                val binding: FragmentNotesRecyclerMarsItemBinding = FragmentNotesRecyclerMarsItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(noteData[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).bind(noteData[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (noteData[position].descriptionNote.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
    }

    override fun getItemCount(): Int {
        return noteData.size
    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(dataNote: DataNote) {
            FragmentNotesRecyclerEarthItemBinding.bind(itemView).apply {
                earthTitle.text = dataNote.titleText
                earthDescription.text = dataNote.descriptionNote
                earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(dataNote)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(dataNote: DataNote) {
            FragmentNotesRecyclerMarsItemBinding.bind(itemView).apply {
                marsNoteTitle.text = dataNote.titleText
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(dataNote)
                }
            }
        }
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1

    }
}