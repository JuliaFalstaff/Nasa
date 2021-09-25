package com.example.nasaapp.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentNoteBinding

import com.example.nasaapp.model.data.DataNote

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var noteData = mutableListOf<Pair<DataNote, Boolean>>()
    lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotes()
        val adapter = RecyclerNoteAdapter(noteData,
                object : OnListItemClickListener {
                    override fun onItemClick(dataNote: DataNote) {
                        Toast.makeText(context, dataNote.titleText, Toast.LENGTH_SHORT).show()
                    }
                }, object : RecyclerNoteAdapter.OnStartDragListener {

            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        })
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener { adapter.addLastItem() }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)
    }

    private fun initNotes() {
        noteData = mutableListOf(
                Pair(DataNote("Earth", "description note"), false),
                Pair(DataNote("Earth", "description note"), false),
                Pair(DataNote("Earth", "description note"), false),
                Pair(DataNote("Earth", "description note"), false),
                Pair(DataNote("Earth", "description note"), false)
        )
        noteData.add(0, Pair(DataNote(getString(R.string.notes_header), "description note"), false))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


class ItemTouchHelperCallback(private val adapter: RecyclerNoteAdapter) :
        ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
                dragFlags,
                swipeFlags
        )
    }

    override fun onMove(
            recyclerView: RecyclerView,
            source: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
    ): Boolean {
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
        itemViewHolder.onItemClear()
    }
}