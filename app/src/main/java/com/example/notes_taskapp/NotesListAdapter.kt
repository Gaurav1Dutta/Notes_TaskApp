package com.example.notes_taskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class NotesListAdapter(
    var list: ArrayList<NotesEntites>,
    var ClickInterface: Notes_List_Fragment
) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return  list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }
//for showing of layout notes reasource in notes main fragment
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_notes_reasource, parent, false)
        var title = view.findViewById<TextView>(R.id.tvTitle)
        var description = view.findViewById<TextView>(R.id.tvDescription)
   //     var imageView = view.findViewById<ImageView>(R.id.imageView)
        var dateSelected = view.findViewById<TextView>(R.id.tvDateSelected1)
        var timeSelected = view.findViewById<TextView>(R.id.tvTimeSelected1)
        title.setText(list[position].title)
        description.setText(list[position].description)
        dateSelected.setText(list[position].date)
        timeSelected.setText(list[position].time)

        var btnEdit = view.findViewById<Button>(R.id.btnEdit) //called the buttons invoked the buttons to work on them
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        btnDelete.setOnClickListener {
            ClickInterface.onDeleteClick(list[position])
        }
        btnEdit.setOnClickListener {
           ClickInterface.onEditClick(list[position])
        }
        return view
    }
}