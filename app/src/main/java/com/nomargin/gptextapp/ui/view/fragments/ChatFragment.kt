package com.nomargin.gptextapp.ui.view.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.databinding.FragmentChatBinding
import com.nomargin.gptextapp.ui.view.adapters.MessageAdapter
import com.nomargin.gptextapp.ui.viewmodel.ChatViewModel
import com.nomargin.gptextapp.util.constants.ApplicationConstants
import com.nomargin.gptextapp.util.model.MessageModel

class ChatFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding get() = _binding!!
    private val messageList: ArrayList<MessageModel> = arrayListOf()
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observers()
        initClicks()
        initRecyclerView()

        binding.applicationCredits.setLinkTextColor(requireContext().getColor(R.color.low_white))
        binding.applicationCredits.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView(){
        messageAdapter = MessageAdapter(requireContext())
        val linearLayoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
            reverseLayout = false
        }
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = messageAdapter
        binding.recyclerView.scrollToPosition(messageList.lastIndex)
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonSendMessage.id -> {
                messageList.add(MessageModel(ApplicationConstants.Companion.MessageType.MESSAGE_SENDED, binding.inputMessage.text.toString()))
                messageAdapter.getMessageList(messageList)
                viewModel.sendMessageToBot(MessageModel(ApplicationConstants.Companion.MessageType.MESSAGE_SENDED, binding.inputMessage.text.toString()))
                binding.inputMessage.text.clear()
            }
        }
    }

    private fun initClicks(){
        binding.buttonSendMessage.setOnClickListener(this)
    }

    private fun observers(){
        viewModel.messageReceived.observe(viewLifecycleOwner){messageListReceived ->
            messageList.add(messageListReceived)
            messageAdapter.getMessageList(messageList)
        }
    }
}