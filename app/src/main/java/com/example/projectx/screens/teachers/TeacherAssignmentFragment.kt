package com.example.projectx.screens.teachers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.adapter.AssignmentAdapter
import com.example.projectx.daos.AssignmentDao
import com.example.projectx.daos.StudentDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.databinding.FragmentTeacherAssignmentBinding
import com.example.projectx.models.Assignment
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth

class TeacherAssignmentFragment : Fragment() {

    private var _binding : FragmentTeacherAssignmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AssignmentAdapter
    private lateinit var assignmentDao: AssignmentDao
    private lateinit var teacherDao: TeacherDao
    private lateinit var studentDao: StudentDao
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTeacherAssignmentBinding.inflate(inflater, container, false)


        binding.create.setOnClickListener {
            val action = TeacherAssignmentFragmentDirections.actionTeacherAssignmentFragmentToCreateAssignmentFragment()
            requireView().findNavController().navigate(action)
        }
        auth = FirebaseAuth.getInstance()

        setUpRecyclerView()
        val view = binding.root
        return view
    }


    private fun setUpRecyclerView() {
        binding.progressBar.progress

        assignmentDao = AssignmentDao()
        studentDao = StudentDao()
        teacherDao = TeacherDao()
        val assignmentCollections = assignmentDao.assignmentCollection

        val snapshot = teacherDao.getTeacherById(auth.currentUser!!.uid).addOnCompleteListener {
            var temp = it.result
            val name = temp.getString("name")
            val query = assignmentCollections.whereEqualTo("teacherName", name)
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