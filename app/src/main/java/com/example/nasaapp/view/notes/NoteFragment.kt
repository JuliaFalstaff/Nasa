package com.example.nasaapp.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasaapp.databinding.FragmentNoteBinding
import com.example.nasaapp.model.data.DataNote

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var noteData = mutableListOf<DataNote>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotes()
        binding.recyclerViewNotes.adapter = RecyclerNoteAdapter(noteData,
                object : OnListItemClickListener {
                    override fun onItemClick(dataNote: DataNote) {
                        Toast.makeText(context, dataNote.titleText, Toast.LENGTH_SHORT).show()
                    }
                }
        )
    }

    private fun initNotes() {
        noteData = mutableListOf(
                DataNote("Earth", "first note"),
                DataNote("Mars", ""),
                DataNote("Earth", "third note"),
                DataNote("Mars", ""),
                DataNote("Earth", "fourth note"),
                DataNote("Mars", ""),
                DataNote("Earth", "third note"),
                DataNote("Mars", ""),
                DataNote("Earth", "fourth note"),
                DataNote("Mars", ""),
                DataNote("Earth", "fourth note"),
                DataNote("Mars", ""),
                DataNote("Earth", "third note"),
                DataNote("Mars", ""),
                DataNote("Earth", "fourth note"),
                DataNote("Mars", "")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}