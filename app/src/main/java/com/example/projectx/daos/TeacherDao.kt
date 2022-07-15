package com.example.projectx.daos

import com.example.projectx.models.Teacher
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeacherDao {
    val db = FirebaseFirestore.getInstance()
    val studentCollection = db.collection("teacher")

    fun addTeacher(teacher: Teacher){
        teacher?.let {
            GlobalScope.launch(Dispatchers.IO) {
                studentCollection.document(teacher.uid).set(it)
            }
        }
    }

    fun getTeacherById(uid : String) : Task<DocumentSnapshot> {
        return studentCollection.document(uid).get()
    }



}