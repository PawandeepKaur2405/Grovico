package com.example.projectx.screens.teachers

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.adapter.MyClassAdapter
import com.example.projectx.daos.MyClassDao
import com.example.projectx.daos.StudentDao
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentTeachersBinding
import com.example.projectx.models.MyClass
import com.example.projectx.screens.student.StudentFragmentDirections
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class TeachersFragment : Fragment() {
    private var _binding: FragmentTeachersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyClassAdapter
    private lateinit var classes: List<MyClass>
    private val USER_TYPE: Int = 1 //1 for Teacher
    private lateinit var myClassDao: MyClassDao
    val db = FirebaseFirestore.getInstance()
    private lateinit var studentDao : StudentDao
    private lateinit var topDao : TopDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeachersBinding.inflate(inflater, container, false)
        val view = binding.root

        studentDao = StudentDao()
        topDao = TopDao()

        binding.apply {
            activity?.setActionBar(toolbar2)
            activity?.actionBar?.setDisplayShowTitleEnabled(false)
            val userImage = TopDao().currentUser().photoUrl
            Glide.with(view.context).load(userImage).circleCrop().error(R.drawable.user_error)
                .into(userLogo)

            userLogo.setOnClickListener {
                popUpMenuSetting(view)
            }
        }
        binding.createMeet.setOnClickListener {
            openDialog(view)
        }

        binding.joinMeet.setOnClickListener {
            navigateToJoinClass()
        }

        binding.apply {
            setUpRecyclerView()
        }

        binding.create.setOnClickListener {
            popUpMenu(view)
        }

        return view
    }

    private fun navigateToJoinClass() {
        val action = TeachersFragmentDirections.actionTeachersFragmentToJoinClassFragment()
        requireView().findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    private fun popUpMenuSetting(view: View) {
        val popupMenu: PopupMenu =
            PopupMenu(context, binding.userLogo, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.mini_setting_teacher_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout ->
                    logoutUser()
                R.id.switch_to_teacher ->
                    navigateToStudentFragment()
            }
            true
        })
        popupMenu.show()
    }

    private fun logoutUser() {
        Firebase.auth.signOut()
        navigateToGetStarted()
    }

    private fun navigateToGetStarted() {
        val action = TeachersFragmentDirections.actionTeachersFragmentToGetStartedFragment()
        requireView().findNavController().navigate(action)
    }


    private fun navigateToDetailsFragment(){
        val action = TeachersFragmentDirections.actionTeachersFragmentToDetailsFragment()
        requireView().findNavController().navigate(action)
    }

    private fun setUpRecyclerView() {
        var collection = MyClassDao().getClassCollection()
        var query = collection.whereEqualTo("creator_id", TopDao().userId()).orderBy("semester", Query.Direction.ASCENDING)
        val recyclerOptions = FirestoreRecyclerOptions.Builder<MyClass>().setQuery(query, MyClass::class.java).build()
        adapter = MyClassAdapter(recyclerOptions, USER_TYPE, requireContext())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(view?.context)
        adapter.startListening()
    }

    private fun popUpMenu(view: View) {
        val popupMenu: PopupMenu =
            PopupMenu(context, binding.create, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.create_fabmenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.create_newClass ->
                    openDialog(view)
                R.id.create_meet ->
                        Toast.makeText(requireContext(), "Will be implemented soon...", Toast.LENGTH_SHORT).show()
                R.id.create_timeTable ->
                    findNavController().navigate(R.id.homeNotesFragment)

                R.id.give_assignment ->
                    navigateToAssignment()
            }
            true
        })
        popupMenu.show()
    }

    private fun navigateToAssignment(){
        val action = TeachersFragmentDirections.actionTeachersFragmentToTeacherAssignmentFragment()
        requireView().findNavController().navigate(action)
    }

    private fun openDialog(view: View) {
        val dialog = BottomSheetDialog(view.context)
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)
        val cources = resources.getStringArray(R.array.Cources)
        val semester = resources.getStringArray(R.array.Semester)
        val section = resources.getStringArray(R.array.Section)
        val courcesAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, cources)
        val semAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, semester)
        val secAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, section)
        val courceView = view.findViewById<MaterialAutoCompleteTextView>(R.id.course_drop)
        val subjectView = view.findViewById<MaterialAutoCompleteTextView>(R.id.section_drop2)
        val semView = view.findViewById<MaterialAutoCompleteTextView>(R.id.semester_drop)
        val secView = view.findViewById<MaterialAutoCompleteTextView>(R.id.section_drop)
        val create_button = view.findViewById<Button>(R.id.create_button)
        val cancel_button = view.findViewById<Button>(R.id.cancel_button)
        courceView.setAdapter(courcesAdapter)
        semView.setAdapter(semAdapter)
        secView.setAdapter(secAdapter)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        create_button.setOnClickListener {
            val myClass = MyClass(
                course = courceView.text.toString(),
                subject = subjectView.text.toString(),
                semester = semView.text.toString(),
                section = secView.text.toString(),
                creator_id = TopDao().userId(),
                students_id = listOf()
            )
            val myClassDao = MyClassDao()
            myClassDao.addClass(myClass)
            dialog.dismiss()
            requireView().refreshDrawableState()

            val navController = findNavController()
            navController.run {
                popBackStack()
                navigate(R.id.teachersFragment)
            }

        }
        cancel_button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }


    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun navigateToStudentFragment(){
        binding.mainCons.visibility = View.INVISIBLE
        binding.loaderCons.visibility = View.VISIBLE

        studentDao = StudentDao()
        val snapshot2 =
            studentDao.getStudentById(topDao.userId())
                .addOnCompleteListener {
                    var teacherResult = it.result.exists()
                    if (!teacherResult) {
                            navigateToDetailsFragment()
                    } else {
                        val action =
                            TeachersFragmentDirections.actionTeachersFragmentToStudentFragment()
                        requireView().findNavController().navigate(action)
                    }
                }


    }


}