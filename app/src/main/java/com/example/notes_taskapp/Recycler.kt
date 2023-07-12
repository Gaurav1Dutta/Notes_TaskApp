package com.example.notes_taskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Recycler(
    var taskList : ArrayList<TaskEntites>,
    var taskClickInterface: TaskClickInterface) :RecyclerView.Adapter<Recycler.ViewHolder>(){
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view){
     var text = view.findViewById<TextView>(R.id.tv_layout_task)
     var complete = view.findViewById<Button>(R.id.btnCompleteTask)
     var EditTask = view.findViewById<Button>(R.id.btnEditTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var adapter = LayoutInflater.from(parent.context).inflate(R.layout.layout_task_reasource,parent, false)
        return ViewHolder(adapter)
    }

    override fun getItemCount(): Int {
        return  taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.setText(taskList[position].Task1)

        holder.complete.setOnClickListener {
            taskClickInterface.onCompleteClick(taskList[position])
        }
        holder.EditTask.setOnClickListener {
            taskClickInterface.onEditTask(taskList[position])
        }
    }

}