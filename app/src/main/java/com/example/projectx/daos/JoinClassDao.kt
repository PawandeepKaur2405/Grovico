package com.example.projectx.daos

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JoinClassDao {
    val db = FirebaseFirestore.getInstance()
    val classCollection = db.collection("classes")

    fun joinClass(classId: String?) {
        classId?.let {
            GlobalScope.launch(Dispatchers.IO) {
                classCollection.document(classId)
                    .update("students_id", FieldValue.arrayUnion(TopDao().userId()))
            }
        }
    }
}