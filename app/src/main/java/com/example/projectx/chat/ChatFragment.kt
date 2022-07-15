package com.example.projectx.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.adapter.ChatAdapter
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentChatFragmetBinding
import com.example.projectx.models.ChatMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {
    private var _binding: FragmentChatFragmetBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messageList: ArrayList<ChatMessage>
    private lateinit var receiverRoom: String
    private lateinit var senderRoom: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatFragmetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setActionBar(binding.toolbar3)
        activity?.actionBar?.setDisplayShowTitleEnabled(true)
        val receiverName = requireArguments().getString("receiver_name")
        activity?.setTitle(receiverName)
        val receiverId: String? = requireArguments().getString("receiver_id")
        val senderID: String = TopDao().userId()
        senderRoom = receiverId + senderID
        receiverRoom = senderID + receiverId
        messageList = ArrayList()
        chatAdapter = ChatAdapter(requireView().context, messageList)

        //setting recyclerview

        binding.messageRecycler.layoutManager = LinearLayoutManager(view.context)
        binding.messageRecycler.adapter = chatAdapter

        TopDao().realDb().child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (snapshotdata in snapshot.children) {
                        val message = snapshotdata.getValue(ChatMessage::class.java)
                        messageList.add(message!!)
                    }
                    chatAdapter.notifyDataSetChanged()
                    binding.messageRecycler.scrollToPosition(messageList.size-1)

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        //sending messages
        binding.apply {
            send.setOnClickListener {
                val message = message.text.toString()
                val messageObject = ChatMessage(message, senderID)
                TopDao().realDb().child("chats").child(senderRoom).child("messages").push()
                    .setValue(messageObject).addOnCompleteListener {
                        TopDao().realDb().child("chats").child(receiverRoom).child("messages")
                            .push()
                            .setValue(messageObject)
                    }
                binding.message.setText("")

            }
        }
    }

}