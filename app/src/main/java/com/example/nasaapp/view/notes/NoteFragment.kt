package com.example.nasaapp.view.notes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentNoteBinding
import com.example.nasaapp.model.data.DataNote
import com.example.nasaapp.view.MainActivity
import com.example.nasaapp.view.picture.PODFragment

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var noteData = mutableListOf<Pair<DataNote, Boolean>>()
     lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var adapter: RecyclerNoteAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar()
        initNotes()
        adapter = RecyclerNoteAdapter(noteData,
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

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomNoteAppBar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_app_bar_note, menu)
        val search = menu.findItem(R.id.bottom_action_search_note)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
                }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_action_search_note -> Toast.makeText(context, R.string.search, Toast.LENGTH_SHORT).show()
            R.id.bottom_action_home_note-> openFragment(PODFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack("")
                    .commitAllowingStateLoss()
        }
    }

    private fun initNotes() {
        noteData = mutableListOf(
                Pair(DataNote(getString(R.string.title_note), getString(R.string.title_description)), false),
                Pair(DataNote(getString(R.string.title_note), getString(R.string.title_description)), false),
                Pair(DataNote(getString(R.string.title_note), getString(R.string.title_description)), false),
                Pair(DataNote(getString(R.string.title_note), getString(R.string.title_description)), false),
                Pair(DataNote(getString(R.string.title_note), getString(R.string.title_description)), false)
        )
        noteData.add(0, Pair(DataNote(getString(R.string.notes_header), getString(R.string.title_description)), false))
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