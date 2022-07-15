package com.example.projectx.daos

import com.example.projectx.models.Assignment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AssignmentDao {
        val db = FirebaseFirestore.getInstance()
        val assignmentCollection = db.collection("assignment")

        fun addAssignment(assignment: Assignment?){

            assignment?.let {
                GlobalScope.launch(Dispatchers.IO) {
                    assignmentCollection.document().set(assignment)
                }
            }
        }
}