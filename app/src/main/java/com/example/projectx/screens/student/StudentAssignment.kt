package com.example.projectx.screens.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.adapter.AssignmentAdapter
import com.example.projectx.daos.AssignmentDao
import com.example.projectx.daos.StudentDao
import com.example.projectx.databinding.FragmentStudentAssignmentBinding
import com.example.projectx.models.Assignment
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth


class StudentAssignment : Fragment() {

    private var _binding: FragmentStudentAssignmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var assignmentDao: AssignmentDao
    private lateinit var studentDao: StudentDao
    private lateinit var adapter: AssignmentAdapter

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStudentAssignmentBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.progressBar.progress
        assignmentDao = AssignmentDao()
        studentDao = StudentDao()
        val assignmentCollections = assignmentDao.assignmentCollection

        studentDao.getStudentById(auth.currentUser!!.uid).addOnCompleteListener {
            val temp = it.result
            val course = temp.getString("course")
            val semester = temp.getString("semester")
            val section = temp.getString("section")
            val code = course + semester + section
            val query = assignmentCollections.whereEqualTo("code", code)
            val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Assignment>()
                .setQuery(query, Assignment::class.java).build()

            adapter = AssignmentAdapter(recyclerViewOptions)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.startListening()
            binding.progressBar.visibility = View.INVISIBLE
        }


    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}