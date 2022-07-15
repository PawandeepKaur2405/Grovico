package com.example.projectx.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.projectx.R
import com.example.projectx.databinding.FragmentCreateNoteBinding
import com.example.projectx.entities.Notes
import com.example.projectx.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : Fragment() {

    private var _binding : FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private var priority : String = "1"

    val viewModel : NotesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)

        binding.saveNotes.setOnClickListener {
            createNote()
        }

        binding.ivGreenDot.setImageResource(R.drawable.done)

        binding.ivRedDot.setOnClickListener {
            binding.ivRedDot.setImageResource(R.drawable.done)
            priority = "3"
            binding.ivYellowDot.setImageResource(0)
            binding.ivGreenDot.setImageResource(0)
        }

        binding.ivGreenDot.setOnClickListener {
            binding.ivGreenDot.setImageResource(R.drawable.done)
            priority = "1"
            binding.ivRedDot.setImageResource(0)
            binding.ivYellowDot.setImageResource(0)
        }

        binding.ivYellowDot.setOnClickListener {
            binding.ivYellowDot.setImageResource(R.drawable.done)
            priority = "2"
            binding.ivRedDot.setImageResource(0)
            binding.ivGreenDot.setImageResource(0)
        }

        val view = binding.root
        return view
    }

    private fun createNote(){
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val note = binding.etNoteBody.text.toString()


        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val notes = Notes(null, title, subTitle, note, currentDate, priority)
        viewModel.addNotes(notes)

        requireView().findNavController().popBackStack()

    }



}