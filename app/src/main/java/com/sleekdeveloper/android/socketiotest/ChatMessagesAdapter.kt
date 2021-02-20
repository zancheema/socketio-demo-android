package com.sleekdeveloper.android.socketiotest

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChatMessagesAdapter(private val userName: String) :
    ListAdapter<ChatMessage, ChatMessagesAdapter.ViewHolder>(ChatMessagesDiffUtil()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatMessage: ChatMessage) {
            val chatMessageContainer = itemView.findViewById<LinearLayout>(R.id.chatMessageContainer)
            val message = itemView.findViewById<TextView>(R.id.message)
            val context = itemView.context

            message.text = chatMessage.content
            if (chatMessage.from == userName) {
                chatMessageContainer.gravity = Gravity.END
                message.apply {
                    setTextColor(ContextCompat.getColor(context, R.color.accent_color_dark))
                    background = ContextCompat.getDrawable(context, R.drawable.my_message_bg)
                }
            } else {
                chatMessageContainer.gravity = Gravity.START
                message.apply {
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    background = ContextCompat.getDrawable(context, R.drawable.other_message_bg)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_message_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChatMessagesDiffUtil : ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem == newItem
    }

}
