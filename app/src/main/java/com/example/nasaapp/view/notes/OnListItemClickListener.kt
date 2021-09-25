package com.example.nasaapp.view.notes

import com.example.nasaapp.model.data.DataNote

interface OnListItemClickListener {
    fun onItemClick(dataNote: DataNote)
}