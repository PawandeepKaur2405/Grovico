package com.example.projectx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.databinding.ItemNotesBinding
import com.example.projectx.entities.Notes
import com.example.projectx.screens.notes.HomeNotesFragmentDirections

class NotesAdapter(private val noteList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val data = noteList[position]
        holder.binding.tvTitle.text = data.title
        holder.binding.tvSubtitle.text = data.subTitle
        holder.binding.tvDate.text = data.date

        when (data.priority) {
            "1" -> holder.binding.vPriority.setBackgroundResource(R.drawable.green_dot)

            "2" -> holder.binding.vPriority.setBackgroundResource(R.drawable.yellow_dot)

            "3" -> holder.binding.vPriority.setBackgroundResource(R.drawable.red_dot)
        }

        holder.binding.root.setOnClickListener {
            val action =
                HomeNotesFragmentDirections.actionHomeNotesFragmentToEditNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int = noteList.size


}