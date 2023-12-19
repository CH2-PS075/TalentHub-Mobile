package com.ch2ps075.talenthub.ui.profile.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityChatBotBinding

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private var messageList: MutableList<Message> = ArrayList()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }

        messageAdapter = MessageAdapter(messageList)
        binding.rvChatBot.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@ChatBotActivity).apply { stackFromEnd = true }
        }

        binding.sendMessageButton.setOnClickListener {
            when {
                binding.edMessage.text.toString().isEmpty() -> {
                    binding.edMessage.error = getString(R.string.error_empty_field)
                }

                else -> {
                    val message = binding.edMessage.text.toString().trim()
                    addToChat(message)
                    binding.edMessage.text.clear()
                    addResponse(message)
                    binding.tvWelcomeChatBot.visibility = View.GONE
                }
            }
        }
    }

    private fun addToChat(message: String) {
        runOnUiThread {
            messageList.add(Message(message, Message.SENT_BY_USER))
            messageAdapter.notifyItemInserted(messageList.size - 1)
            binding.rvChatBot.smoothScrollToPosition(messageList.size - 1)
        }
    }

    private fun addResponse(responseMessage: String) {
        runOnUiThread {
            messageList.add(Message(responseMessage, Message.SENT_BY_BOT))
            messageAdapter.notifyItemChanged(messageList.size - 1)
            binding.rvChatBot.smoothScrollToPosition(messageList.size - 1)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}