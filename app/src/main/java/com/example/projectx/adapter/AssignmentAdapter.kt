package com.example.projectx.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.databinding.AssignmentItemBinding
import com.example.projectx.models.Assignment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class AssignmentAdapter(options: FirestoreRecyclerOptions<Assignment>) :
    FirestoreRecyclerAdapter<Assignment, AssignmentAdapter.AssignmentViewHolder>(options) {

    class AssignmentViewHolder(val binding: AssignmentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssignmentAdapter.AssignmentViewHolder {

        return AssignmentAdapter.AssignmentViewHolder(
            AssignmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int, model: Assignment) {

        holder.binding.AssignmentHeading.text = model.assignmentHeading
        holder.binding.TeacherName.text = "By: " + model.teacherName
        holder.binding.subjectLabel.text = model.subject
        holder.binding.dueDate.text = "Due Date: " + model.dueDate
        holder.binding.assignmentConstraint.setOnClickListener {
            val bundle = bundleOf("model" to model)
            Navigation.findNavController(it).navigate(R.id.assignmentDetailFragment, bundle)
        }
    }


}
