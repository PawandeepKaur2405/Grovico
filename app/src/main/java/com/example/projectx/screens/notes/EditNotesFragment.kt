package com.example.projectx.screens.notes

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projectx.R
import com.example.projectx.databinding.FragmentEditNotesBinding
import com.example.projectx.entities.Notes
import com.example.projectx.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding : FragmentEditNotesBinding
    var priority: String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        

        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)

        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Edit Your Note"
        setHasOptionsMenu(true)

        binding.etTitle.setText(oldNotes.data.title)
        binding.etSubtitle.setText(oldNotes.data.subTitle)
        binding.etNoteBody.setText(oldNotes.data.notes)

        when(oldNotes.data.priority){
            "1" -> {
                binding.ivGreenDot.setImageResource(R.drawable.done)
                priority = "1"
                binding.ivRedDot.setImageResource(0)
                binding.ivYellowDot.setImageResource(0)
            }

            "2" -> {
                binding.ivYellowDot.setImageResource(R.drawable.done)
                priority = "2"
                binding.ivRedDot.setImageResource(0)
                binding.ivGreenDot.setImageResource(0)
            }
            "3" -> {
                binding.ivRedDot.setImageResource(R.drawable.done)
                priority = "3"
                binding.ivYellowDot.setImageResource(0)
                binding.ivGreenDot.setImageResource(0)
            }
        }

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

        binding.saveNotes.setOnClickListener {
            updateNote()
        }
        return binding.root
    }

    private fun updateNote(){
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val note = binding.etNoteBody.text.toString()


        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val notes = Notes(oldNotes.data.id, title, subTitle, note, currentDate, priority)
        viewModel.updateNote( notes)

        val action = EditNotesFragmentDirections.actionEditNotesFragmentToHomeNotesFragment()
        requireView().findNavController().popBackStack()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDeleteNote){
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.btYes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.btNo)

            textViewYes?.setOnClickListener {
                viewModel.deleteNote(oldNotes.data.id!!)
                bottomSheet.dismiss()
                requireView().findNavController().popBackStack()

            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)


    }

}