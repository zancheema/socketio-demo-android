package com.sleekdeveloper.android.socketiotest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = arguments?.getString("userName")!!
        val chatRoom = arguments?.getString("chatRoom")!!

        val chatMessagesAdapter = ChatMessagesAdapter(userName)
        chatMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatMessagesAdapter

            listOf(
                ChatMessage(userName, "Hey", "chat_1"),
                ChatMessage("otherUser", "Hello", "chat_1"),
                ChatMessage(userName, "How's it going", "chat_1")
            ).let {
                chatMessagesAdapter.submitList(it)
            }
        }
    }
}