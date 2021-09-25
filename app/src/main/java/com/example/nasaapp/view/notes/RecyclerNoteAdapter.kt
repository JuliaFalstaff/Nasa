package com.example.nasaapp.view.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.databinding.FragmentNotesRecyclerEarthItemBinding
import com.example.nasaapp.databinding.FragmentNotesRecyclerHeaderItemBinding
import com.example.nasaapp.databinding.FragmentNotesRecyclerMarsItemBinding
import com.example.nasaapp.model.data.DataNote

class RecyclerNoteAdapter(
        private var noteData: MutableList<Pair<DataNote, Boolean>>,
        private var onListItemClickListener: OnListItemClickListener,
) : RecyclerView.Adapter<BaseNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseNoteViewHolder {
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
                val binding: FragmentNotesRecyclerHeaderItemBinding = FragmentNotesRecyclerHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseNoteViewHolder, position: Int) {
        holder.bind(noteData[position])
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return TYPE_HEADER
        return if (noteData[position].first.descriptionNote.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
    }

    override fun getItemCount(): Int {
        return noteData.size
    }

    fun addLastItem() {
        noteData.add(Pair(generateItem(), false))
        notifyDataSetChanged()
    }

    fun generateItem() = DataNote("Earth", "GenerateDescriptions")

    inner class EarthViewHolder(view: View) : BaseNoteViewHolder(view) {
        override fun bind(pairDataNote: Pair<DataNote, Boolean>) {
            FragmentNotesRecyclerEarthItemBinding.bind(itemView).apply {
                earthTitle.text = pairDataNote.first.titleText
                earthDescription.text = pairDataNote.first.descriptionNote
                earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(pairDataNote.first)
                }
                earthAddNoteImageView.setOnClickListener {
                    insertItem()
                }
                earthDeleteImageView.setOnClickListener {
                    removeItem()
                }
                earthDescription.setOnClickListener {
                    toggleText()
                }
                earthFullDescriptionTextView.visibility = if (pairDataNote.second) View.VISIBLE else View.GONE
            }
        }

        private fun insertItem() {
            noteData.add(layoutPosition, Pair(generateItem(), false))
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            noteData.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleText() {
            noteData[layoutPosition] = noteData[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }
    }

    inner class MarsViewHolder(view: View) : BaseNoteViewHolder(view) {
        override fun bind(pairDataNote: Pair<DataNote, Boolean>) {
            FragmentNotesRecyclerMarsItemBinding.bind(itemView).apply {
                marsNoteTitle.text = pairDataNote.first.titleText
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(pairDataNote.first)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseNoteViewHolder(view) {
        override fun bind(pairDataNote: Pair<DataNote, Boolean>) {
            FragmentNotesRecyclerHeaderItemBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(pairDataNote.first)
                }
            }
        }
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }
}