package com.example.projectx.screens.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectx.daos.JoinClassDao
import com.example.projectx.databinding.FragmentJoinClassBinding

class JoinClassFragment : Fragment() {

    private var _binding: FragmentJoinClassBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentJoinClassBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.apply {
            join.setOnClickListener {
                if(classId.text.isNullOrBlank()){
                    classId.error = "Required"
                }else{
                    JoinClassDao().joinClass(classId.text.toString())
                    navigateToStudentfragment()
                }

            }

        }
        return view
    }

    private fun navigateToStudentfragment() {
        val action = JoinClassFragmentDirections.actionJoinClassFragmentToStudentFragment()
        requireView().findNavController().navigate(action)
    }


}