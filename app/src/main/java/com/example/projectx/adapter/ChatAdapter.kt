package com.example.projectx.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.daos.TopDao
import com.example.projectx.models.ChatMessage

class ChatAdapter(private val context: Context, private val messageList: ArrayList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RECIVED = 2

    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sMessageText: TextView? = itemView.findViewById(R.id.sender_message)
    }

    class ReciverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rMessageText: TextView? = itemView.findViewById(R.id.reciver_message)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.sender_bubble, parent, false )
            return SenderViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(context).inflate(R.layout.reciver_bubble, parent, false )
            return ReciverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curretMessage = messageList[position]
        if (holder.javaClass == SenderViewHolder::class.java) {
            val viewHolder = holder as SenderViewHolder
            holder.sMessageText?.text = curretMessage.message
        } else if (holder.javaClass == ReciverViewHolder::class.java) {
            val viewHolder = holder as ReciverViewHolder
            holder.rMessageText?.text = curretMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(TopDao().userId().equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECIVED
        }
    }

    override fun getItemCount(): Int {
        return  messageList.size
    }
}