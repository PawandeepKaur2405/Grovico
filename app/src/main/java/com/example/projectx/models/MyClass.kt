package com.example.projectx.models

data class MyClass(
    val course: String = "",
    val subject: String= "",
    val semester: String= "",
    val section: String= "",
    val creator_id: String= "",
    val students_id : List<String> = listOf()
)
