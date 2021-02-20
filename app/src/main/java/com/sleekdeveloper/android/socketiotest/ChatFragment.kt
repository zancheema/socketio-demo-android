package com.sleekdeveloper.android.socketiotest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private lateinit var chatMessagesAdapter: ChatMessagesAdapter
    private lateinit var mSocket: Socket
    private lateinit var userName: String
    private lateinit var chatRoom: String

    val gson = Gson()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = arguments?.getString("userName")!!
        chatRoom = arguments?.getString("chatRoom")!!

        chatMessagesAdapter = ChatMessagesAdapter(userName)
        chatMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatMessagesAdapter
        }

        try {
            mSocket = IO.socket("http://10.0.2.2:3000")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect: ${e.message}")
        }

        mSocket.connect()
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("newSubscriber", onNewSubscriber)
        mSocket.on("newUnsubscriber", onNewUnsubscriber)
    }

    private val onConnect = Emitter.Listener {
        val data = Subscriber(userName, chatRoom)
        val jsonData = gson.toJson(data)
        mSocket.emit("subscribe", jsonData)
    }

    private val onNewSubscriber = Emitter.Listener {
        val name = it[0] as String
        addToChatMessages(ChatMessage(name, "$name joined the chat room", chatRoom))
    }

    private val onNewUnsubscriber = Emitter.Listener {
        val name = it[0] as String
        addToChatMessages(ChatMessage(name, "$name left the chat room", chatRoom))
    }

    private fun addToChatMessages(chatMessage: ChatMessage) {
        val tmp = ArrayList(chatMessagesAdapter.currentList)
        tmp.add(chatMessage)
        requireActivity().runOnUiThread {
            chatMessagesAdapter.submitList(tmp)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val subscriber = Subscriber(userName, chatRoom)
        mSocket.emit("unsubscribe", gson.toJson(subscriber))
        mSocket.disconnect()
    }
}