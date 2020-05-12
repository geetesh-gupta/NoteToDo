package com.gg.notetodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gg.notetodo.R
import com.gg.notetodo.clicklisteners.ItemClickListener
import com.gg.notetodo.db.Notes

class NotesAdapter(
    private val list: List<Notes>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = list[position]
        val title = notes.title
        val description = notes.description
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description
        holder.checkBoxMarkStatus.isChecked = notes.isTaskCompleted
        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
        holder.checkBoxMarkStatus.setOnCheckedChangeListener { _, isChecked ->
            notes.isTaskCompleted = isChecked
            itemClickListener.onUpdate(notes)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.notesAdapterTitle)
        var textViewDescription: TextView = itemView.findViewById(R.id.notesAdapterDescription)
        var checkBoxMarkStatus: CheckBox = itemView.findViewById(R.id.notesAdapterCheckbox)
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.hashCode().toLong()
    }
}
