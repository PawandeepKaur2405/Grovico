package com.example.projectx.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.daos.TopDao
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MyClassAdapter(
    options: FirestoreRecyclerOptions<MyClass>,
    private val user_type: Int,
    private val context: Context
) :
    FirestoreRecyclerAdapter<MyClass, MyClassAdapter.ClassViewHolder>(options) {

    class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseLabel: TextView = itemView.findViewById(R.id.course_label)
        val subjectLabel: TextView = itemView.findViewById(R.id.subject_label)
        val totalAll: TextView = itemView.findViewById(R.id.total_all)
        val totalShow: CardView = itemView.findViewById(R.id.total_show)
        val user1: ImageView = itemView.findViewById(R.id.user1)
        val user2: ImageView = itemView.findViewById(R.id.user2)
        val user3: ImageView = itemView.findViewById(R.id.user3)
        val classItem: ConstraintLayout = itemView.findViewById(R.id.class_groupBtn)
        val studentNames: TextView = itemView.findViewById(R.id.stu)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.myclass_item, parent, false)
        return ClassViewHolder(view)

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClassViewHolder, position: Int, model: MyClass) {
        val array = ArrayList<List<String>>()
        holder.courseLabel.text = "${model.course}  Sem-${model.semester}"
        holder.subjectLabel.text = "${model.subject} : Sec-${model.section}"
        if (model.students_id.isNotEmpty()) {
            TopDao().dbRef().collection("student")
                .whereIn("uid", model.students_id).limit(8)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        array.add(
                            listOf(
                                document.get("name").toString(),
                                document.get("imageUrl").toString()
                            )
                        )
                    }

                    //adding student names in class item
                    val namesArray = ArrayList<String>()
                    for (i in array) {
                        namesArray.add(i[0])
                    }
                    holder.studentNames.text = namesArray.toString()
                        .replace("[", "").replace("]", "").trim()



                    when (model.students_id.size) {
                        1 -> {
                            Glide.with(context).load(array[0][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user1)
                            holder.user2.visibility = View.GONE
                            holder.user3.visibility = View.GONE
                            holder.totalShow.visibility = View.GONE
                        }
                        2 -> {
                            Glide.with(context).load(array[0][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user1)
                            Glide.with(context).load(array[1][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user2)
                            holder.user3.visibility = View.GONE
                            holder.totalShow.visibility = View.GONE


                        }
                        3 -> {
                            Glide.with(context).load(array[0][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user1)
                            Glide.with(context).load(array[1][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user2)
                            Glide.with(context).load(array[2][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user3)
                            holder.totalShow.visibility = View.GONE
                        }
                        else -> {
                            Glide.with(context).load(array[0][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user1)
                            Glide.with(context).load(array[1][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user2)
                            Glide.with(context).load(array[2][1]).error(R.drawable.user_error)
                                .circleCrop().into(holder.user3)
                            holder.totalAll.text = "+" + (documents.size() - 3).toString()


                        }
                    }

                }
        } else if (model.students_id.isEmpty()) {
            holder.studentNames.text = "Click to Invite Students"
            holder.totalAll.text = "+0"
        }
        holder.classItem.setOnClickListener {
            val snapshot: String = snapshots.getSnapshot(position).id
            val bundle = Bundle()
            bundle.putString("test", snapshot)
            bundle.putInt("user_type", user_type)
            when (user_type) {
                0 -> {
                    it.findNavController()
                        .navigate(R.id.action_studentFragment_to_classGroupFragment, bundle)
                }
                1 -> {
                    it.findNavController()
                        .navigate(R.id.action_teachersFragment_to_classGroupFragment, bundle)
                }

            }
        }
    }


}
