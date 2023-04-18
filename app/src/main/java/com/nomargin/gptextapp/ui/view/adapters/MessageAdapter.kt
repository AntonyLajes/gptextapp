package com.nomargin.gptextapp.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gptextapp.databinding.MessageReceivedItemBinding
import com.nomargin.gptextapp.databinding.MessageSendedItemBinding
import com.nomargin.gptextapp.util.constants.ApplicationConstants
import com.nomargin.gptextapp.util.model.MessageModel

class MessageAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var messageList: ArrayList<MessageModel> = ArrayList()
    companion object{
        const val MESSAGE_SENDED_ID = 1
        const val MESSAGE_RECEIVED_ID = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == MESSAGE_SENDED_ID){
            val view = MessageSendedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SendedViewHolder(view)
        }else{
            val view = MessageReceivedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceivedViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(currentMessage.role == ApplicationConstants.Companion.MessageType.MESSAGE_SENDED){
            MESSAGE_SENDED_ID
        }else{
            MESSAGE_RECEIVED_ID
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SendedViewHolder::class.java){
            val viewHolder = holder as SendedViewHolder
            viewHolder.item.message.text = currentMessage.content
        }else{
            val viewHolder = holder as ReceivedViewHolder
            viewHolder.item.message.text = currentMessage.content
        }
    }

    override fun getItemCount(): Int = messageList.size

    fun getMessageList(list: ArrayList<MessageModel>){
        messageList = list
        notifyDataSetChanged()
    }

    class SendedViewHolder(var item: MessageSendedItemBinding): RecyclerView.ViewHolder(item.root)
    class ReceivedViewHolder(var item: MessageReceivedItemBinding): RecyclerView.ViewHolder(item.root)

}