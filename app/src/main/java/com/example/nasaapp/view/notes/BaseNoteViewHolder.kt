package com.example.nasaapp.view.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.model.data.DataNote

abstract class BaseNoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind (pairDataNote: Pair<DataNote, Boolean>)
}