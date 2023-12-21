package com.ch2ps075.talenthub.ui.profile.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.network.api.response.BotResponse
import com.ch2ps075.talenthub.data.network.api.response.ReqBody
import com.ch2ps075.talenthub.data.network.api.retrofit.ApiConfig
import com.ch2ps075.talenthub.databinding.ActivityChatBotBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    callApi(message)
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
            if (messageList.isNotEmpty()) {
                messageList.removeAt(messageList.size - 1)
                messageList.add(Message(responseMessage, Message.SENT_BY_BOT))
                messageAdapter.notifyItemChanged(messageList.size - 1)
                binding.rvChatBot.smoothScrollToPosition(messageList.size - 1)
            }
        }
    }

    private fun callApi(message: String) {
        messageList.add(Message(getString(R.string.typing), Message.SENT_BY_BOT))

        val requestBody = ReqBody(text = message)

        val call = ApiConfig.getApiService("").getBotResponse(requestBody)
        call.enqueue(object : Callback<BotResponse> {
            override fun onResponse(call: Call<BotResponse>, response: Response<BotResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.data?.response
                    addResponse(result?.trim() ?: "Empty response")
                } else {
                    Toast.makeText(this@ChatBotActivity,
                        getString(R.string.not_successful), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BotResponse>, t: Throwable) {
                Toast.makeText(this@ChatBotActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}