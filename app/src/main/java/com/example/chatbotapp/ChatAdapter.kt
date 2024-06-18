import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotapp.ChatMessage
import com.example.chatbotapp.R


class ChatAdapter(private val chatMessages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // Define ChatViewHolder class
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views within the view holder if needed
        val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
    }

    // Implement RecyclerView.Adapter methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = chatMessages[position]
        holder.textViewMessage.text = chatMessage.message
        // Handle other binding logic if needed
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    // Add other methods as needed, such as adding messages to the adapter
    fun addMessage(chatMessage: ChatMessage) {
        chatMessages.add(chatMessage)
        notifyItemInserted(chatMessages.size - 1)
    }

}
