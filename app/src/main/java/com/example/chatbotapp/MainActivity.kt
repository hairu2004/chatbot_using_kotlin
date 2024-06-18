package com.example.chatbotapp

import ChatAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotapp.ui.theme.ChatbotAppTheme
import android.view.LayoutInflater
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(getSampleMessages())
        recyclerView.adapter = chatAdapter

        // Set OnClickListener for the "Send" button
        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
            } else {
                // Inform the user if the message is empty
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up TextWatcher for EditText to enable/disable Send button
        editTextMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Enable or disable button based on text length
                buttonSend.isEnabled = s?.isNotBlank() ?: false
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used
            }
        })
    }

    private fun sendMessage(message: String) {
        // Add user message to the adapter
        chatAdapter.addMessage(ChatMessage(message, true))

        // Clear the EditText
        editTextMessage.text.clear()

        // Scroll to the last item in the RecyclerView
        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)

        // Generate and display bot response (simulated delay for response)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000) // Simulate bot response delay
            val response = getBotResponse(message)
            chatAdapter.addMessage(ChatMessage(response, false))
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }

    private fun getBotResponse(userMessage: String): String {
        // Convert user message to lowercase for case-insensitive matching
        val message = userMessage.toLowerCase()

        // Predefined responses based on keywords
        return when {
            message.contains("hello") -> "Hello there! How can I help you?"
            message.contains("how are you") -> "I'm just a program, but I'm doing well. How about you?"
            message.contains("weather") -> "I'm sorry, I can't provide weather information."
            else -> "I'm sorry, I didn't understand that."
        }
    }


    // Function to get sample chat messages
    private fun getSampleMessages(): MutableList<ChatMessage> {
        return mutableListOf(
            
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun ChatScreenPreview() {
        ChatbotAppTheme {
            // Preview code here
        }
    }
}
