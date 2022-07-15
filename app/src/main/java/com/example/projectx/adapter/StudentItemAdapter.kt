package com.example.projectx.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.models.StudentItem

class StudentItemAdapter(
    private val dataList: ArrayList<StudentItem>,
    private val context: Context, private val userType: Int
) :
    RecyclerView.Adapter<StudentItemAdapter.StudentItemViewHolder>() {
    class StudentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.student_name)
        val studentDescription: TextView = itemView.findViewById(R.id.student_descp)
        val studentImage: ImageView = itemView.findViewById(R.id.student_image)
        val studentItemId: ConstraintLayout = itemView.findViewById(R.id.student_itemid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.students_item, parent, false)
        return StudentItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentItemViewHolder, position: Int) {
        val model = dataList[position]
        holder.studentName.text = model.name
        holder.studentDescription.text = "Hey there I am using Grovico"
        Glide.with(context).load(model.imageUrl).circleCrop().error(R.drawable.user_error)
            .into(holder.studentImage)
        holder.studentItemId.setOnClickListener {
            val reciverId = model.id
            val bundle = Bundle()
            bundle.putString("receiver_id", reciverId)
            bundle.putString("receiver_name", model.name)
            it.findNavController().navigate(R.id.action_classGroupFragment_to_chatFragment, bundle)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}