package com.example.projectx.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectx.databinding.FragmentAssignmentDetailBinding
import com.example.projectx.models.Assignment

class AssignmentDetailFragment : Fragment() {
    private var _binding : FragmentAssignmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var assignment : Assignment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAssignmentDetailBinding.inflate(inflater, container, false)
        assignment = arguments?.getParcelable<Assignment>("model")!!

        binding.subject.text = assignment.subject
        binding.assignmentDetail.text = assignment.assignmentBrief
        binding.assignmentHeading.text = assignment.assignmentHeading
        binding.dueDate.text = "Due Date: " + assignment.dueDate
        binding.givenDate.text = "Given At: " + assignment.createdAt
        binding.givenBy.text = "By: " + assignment.teacherName

        return binding.root
    }

}