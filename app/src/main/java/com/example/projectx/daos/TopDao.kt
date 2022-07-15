package com.example.projectx.daos

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class TopDao {
    private val db = FirebaseFirestore.getInstance()
    private val realDb = FirebaseDatabase.getInstance().reference
    private val currentuser = Firebase.auth.currentUser!!

    private val userId = Firebase.auth.currentUser!!.uid


    fun userId(): String {
        return userId
    }
    fun dbRef(): FirebaseFirestore {
        return db
    }
    fun currentUser(): FirebaseUser {
        return currentuser
    }
    fun realDb(): DatabaseReference {
        return realDb
    }
}
