package com.example.nasaapp.view.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.util.toAndroidPair
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.databinding.FragmentNotesRecyclerEarthItemBinding
import com.example.nasaapp.databinding.FragmentNotesRecyclerHeaderItemBinding
import com.example.nasaapp.databinding.FragmentNotesRecyclerMarsItemBinding
import com.example.nasaapp.model.data.DataNote
import java.util.*

class RecyclerNoteAdapter(
        private var noteData: MutableList<Pair<DataNote, Boolean>>,
        private var onListItemClickListener: OnListItemClickListener,
        private var dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseNoteViewHolder>(), ItemTouchHelperAdapter, Filterable {

    var noteDataFiltered: MutableList<Pair<DataNote, Boolean>> = noteData

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
        holder.bind(noteDataFiltered[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            noteData[position].first.descriptionNote.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    override fun getItemCount(): Int {
        return noteDataFiltered.size
    }

    fun addLastItem() {
        noteData.add(Pair(generateItem(), false))
        notifyDataSetChanged()
    }

    fun generateItem() = DataNote("Earth", "GenerateDescriptions")


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        noteData.removeAt(fromPosition).apply {
            noteData.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        noteData.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    noteDataFiltered = noteData
                } else {
                    val resultList: MutableList<Pair<DataNote, Boolean>> = mutableListOf()
                    for (row in noteData) {
                        if (row.first.toString().toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    noteDataFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = noteDataFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                noteDataFiltered = results?.values as MutableList<Pair<DataNote, Boolean>>
                notifyDataSetChanged()
            }
        }
    }

    inner class EarthViewHolder(view: View) : BaseNoteViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(pairDataNote: Pair<DataNote, Boolean>) {
            FragmentNotesRecyclerEarthItemBinding.bind(itemView).apply {
                earthTitle.text = pairDataNote.first.titleText
                earthDescription.text = pairDataNote.first.descriptionNote
                earthImageView.setOnClickListener { onListItemClickListener.onItemClick(pairDataNote.first) }
                earthAddNoteImageView.setOnClickListener { insertItem() }
                earthDeleteImageView.setOnClickListener { removeItem() }
                earthDescription.setOnClickListener { toggleText() }
                earthFullDescriptionTextView.visibility = if (pairDataNote.second) View.VISIBLE else View.GONE

                dragHandleImageView.setOnTouchListener { v, event ->
                    if (MotionEventCompat.getActionMasked(event)  == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this@EarthViewHolder)
                    }
                    false
                }
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

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
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

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }

}

