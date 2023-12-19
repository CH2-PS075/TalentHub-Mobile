package com.ch2ps075.talenthub.ui.profile.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps075.talenthub.databinding.ItemRowChatbotBinding

class MessageAdapter(private val messageList: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(private val binding: ItemRowChatbotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun sentByUser(message: Message) {
            binding.leftChatView.visibility = View.GONE
            binding.rightChatView.visibility = View.VISIBLE
            binding.rightChatTextView.text = message.message
        }

        fun sentByBot(message: Message) {
            binding.rightChatView.visibility = View.GONE
            binding.leftChatView.visibility = View.VISIBLE
            binding.leftChatTextView.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = ItemRowChatbotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sentBy == Message.SENT_BY_USER) {
            holder.sentByUser(message)
        } else {
            holder.sentByBot(message)
        }
    }
}