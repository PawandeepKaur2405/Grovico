package com.example.projectx.screens.student

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.adapter.MyClassAdapter
import com.example.projectx.daos.MyClassDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentStudentBinding
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyClassAdapter
    private val userType: Int = 0 // 0 for student and 1 for Teacher
    private lateinit var teacherDao: TeacherDao
    private lateinit var topDao: TopDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view = binding.root
        topDao = TopDao()

        binding.apply {
            activity?.setActionBar(toolbar2)
            activity?.actionBar?.setDisplayShowTitleEnabled(false)
            val userImage = TopDao().currentUser().photoUrl
            Glide.with(view.context).load(userImage).circleCrop().error(R.drawable.user_error)
                .into(userLogo)
            setUpRecyclerView()
            userLogo.setOnClickListener {
                popUpMenuSetting()
            }



//            joinMeet.setOnClickListener {
//                Toast.makeText(it.context,"Functionality removed due to app size limit...", Toast.LENGTH_SHORT).show()
//            }

            assignmentButton.setOnClickListener {
                navigateToStuAssignment()
            }

            notesButton.setOnClickListener {
                navigateToStuNotes()
            }

            toolbar2.setNavigationOnClickListener {
                Toast.makeText(view.context, "Will be Implemented Soon ...", Toast.LENGTH_SHORT)
                    .show()
            }

            exploreButton.setOnClickListener {
                val location = Uri.parse("https://www.gehu.ac.in/content/gehu/en/academics/engineering/computer-science-and-engineering.html")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)

// Try to invoke the intent.
                try {
                    startActivity(mapIntent)
                } catch (e: ActivityNotFoundException) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
        }


    }


    private fun popUpMenuSetting() {
        val popupMenu =
            PopupMenu(context, binding.userLogo, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.mini_setting_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout ->
                    logoutUser()
                R.id.switch_to_teacher ->
                    navigateToTeacherFragment()
//                    Toast.makeText(
//                        view?.context,
//                        "You are not allowed to switch",
//                        Toast.LENGTH_SHORT
//                    ).show()
                R.id.join_class ->
                    navigateToJoinClass()

                R.id.change_detials ->
                    navigateToDetailsFragment()


            }
            true
        }
        popupMenu.show()
    }

    private fun logoutUser() {
        Firebase.auth.signOut()
        navigateToGetStarted()
    }

    private fun setUpRecyclerView() {
        val collection = MyClassDao().getClassCollection()
        val userId = TopDao().userId()
        val query = collection.whereArrayContains("students_id", userId)
        val recyclerOptions =
            FirestoreRecyclerOptions.Builder<MyClass>().setQuery(query, MyClass::class.java).build()
        adapter = MyClassAdapter(recyclerOptions, userType, requireContext())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter.startListening()


    }


    private fun navigateToGetStarted() {
        val action = StudentFragmentDirections.actionStudentFragmentToGetStartedFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToJoinClass() {
        val action = StudentFragmentDirections.actionStudentFragmentToJoinClassFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToStuNotes() {
        val action = StudentFragmentDirections.actionStudentFragmentToHomeNotesFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToStuAssignment() {
        val action = StudentFragmentDirections.actionStudentFragmentToStudentAssignment()
        requireView().findNavController().navigate(action)
    }


    private fun navigateToDetailsFragment() {
        val action = StudentFragmentDirections.actionStudentFragmentToDetailsFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToTeacherFragment() {
        binding.mainCons.visibility = View.INVISIBLE
        binding.loaderCons.visibility = View.VISIBLE
        teacherDao = TeacherDao()
        teacherDao.getTeacherById(topDao.userId())
            .addOnCompleteListener {
                val teacherResult = it.result.exists()
                if (!teacherResult) {
                    navigateToDetailsFragment()
                } else {
                    val action =
                        StudentFragmentDirections.actionStudentFragmentToTeachersFragment()
                    requireView().findNavController().navigate(action)
                }
            }
    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}

