package com.example.projectx.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.models.ClassOptions

class ClassOptionsAdapter(
    private val class_id: String,
    private val optionsList: List<ClassOptions>,
    private val role: Int
) : RecyclerView.Adapter<ClassOptionsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.c_option_label)
        val icon: ImageView = itemView.findViewById(R.id.c_option_icon)
        val classOptionItem: CardView = itemView.findViewById(R.id.class_otpionItem_card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.class_option_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = optionsList[position]

        if(role == 0){
            when (position) {
                0 -> {
                    holder.classOptionItem.setBackgroundResource(R.drawable.round_filledblue)
                    holder.label.setTextColor(Color.parseColor("#B2C2FF"))
                    holder.classOptionItem.cardElevation = 12F
                    holder.classOptionItem.setOnClickListener {
                        Navigation.findNavController(it).navigate(R.id.studentAssignment)
                    }
                }
                1 -> {
                    holder.classOptionItem.setOnClickListener{
                        Toast.makeText(it.context,"Functionality removed due to app size limit...", Toast.LENGTH_SHORT).show()
                    }
                }

                2 -> {
                    holder.classOptionItem.setOnClickListener{
                        Toast.makeText(it.context,"Functionality removed due to app size limit...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else if(role == 1){
            when (position) {
                0 -> {
                    holder.classOptionItem.setBackgroundResource(R.drawable.round_filledblue)
                    holder.label.setTextColor(Color.parseColor("#B2C2FF"))
                    holder.classOptionItem.cardElevation = 12F
                    holder.classOptionItem.setOnClickListener {
                        Navigation.findNavController(it).navigate(R.id.teacherAssignmentFragment)
                    }

                }
                1 -> {
                    holder.classOptionItem.setOnClickListener {
                        val clipboard: ClipboardManager =
                            it.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("class_id", class_id)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(it.context, "Copied Class Id", Toast.LENGTH_SHORT).show()
                    }

                }

                2 -> {
                    holder.classOptionItem.setOnClickListener{
                        Toast.makeText(it.context, "Will be implemented soon...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        holder.label.text = current.label
        holder.icon.setImageResource(current.imageView)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }
}
