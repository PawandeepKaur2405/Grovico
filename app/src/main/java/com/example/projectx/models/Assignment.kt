package com.example.projectx.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Assignment(
                    val course: String = "",
                    val subject: String= "",
                    val semester: String= "",
                    val section: String= "",
                    val teacherName : String= "",
                    val assignmentHeading : String = "",
                    val assignmentBrief: String = "",
                    val dueDate: String = "",
                    val createdAt: String = "",
                    val code: String = ""
               ) : Parcelable


