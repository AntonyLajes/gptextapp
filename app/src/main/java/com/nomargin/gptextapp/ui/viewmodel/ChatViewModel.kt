package com.nomargin.gptextapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.ui.model.GPTEndpoint
import com.nomargin.gptextapp.ui.model.RetrofitInstance
import com.nomargin.gptextapp.util.constants.ApplicationConstants
import com.nomargin.gptextapp.util.model.MessageModel
import com.nomargin.gptextapp.util.model.NewAnswerModel
import com.nomargin.gptextapp.util.model.NewSendMessageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofitInstance = RetrofitInstance.getRetrofitInstance(
        application.applicationContext.getString(
            R.string.chatgpt_api7
        )
    )
    private val endpoint = retrofitInstance.create(GPTEndpoint::class.java)
    private var _messageReceived: MutableLiveData<MessageModel> = MutableLiveData()
    val messageReceived: LiveData<MessageModel> get() = _messageReceived

    fun sendMessageToBot(message: MessageModel) {
        val callback = endpoint.newGetRetrofitAnswer(NewSendMessageModel(message.content))
        callback.enqueue(object : Callback<NewAnswerModel>{
            override fun onResponse(
                call: Call<NewAnswerModel>,
                response: Response<NewAnswerModel>
            ) {
                _messageReceived.value = MessageModel(ApplicationConstants.Companion.MessageType.MESSAGE_RECEIVED, response.body()?.response ?: "Empty")
            }

            override fun onFailure(call: Call<NewAnswerModel>, t: Throwable) {
                println(t.message)
            }
        })
    }

}