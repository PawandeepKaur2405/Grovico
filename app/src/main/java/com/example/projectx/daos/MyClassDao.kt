package com.example.projectx.daos

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.example.projectx.models.MyClass
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyClassDao {
    private val db = FirebaseFirestore.getInstance()
    private val myClassCollection = db.collection("classes")
    private val userId = Firebase.auth.currentUser!!.uid
    private val teacherRef = db.collection("teacher").document(userId)
    private lateinit var classes: List<MyClass>

    fun addClass(myClass: MyClass?) {
        myClass?.let {
            GlobalScope.launch(Dispatchers.IO) {
                val myClassCol = myClassCollection.document()
                myClassCol.set(it).addOnSuccessListener {
                    teacherRef.update("myClass", FieldValue.arrayUnion(myClassCol.id))
                }
                    .addOnFailureListener {
                        Log.i(TAG, "Failed Setting MyClass data in Firebase")
                    }
            }
        }
    }

    fun getClassCollection(): CollectionReference {
        return myClassCollection
    }


}