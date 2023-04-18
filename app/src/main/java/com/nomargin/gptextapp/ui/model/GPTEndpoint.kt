package com.nomargin.gptextapp.ui.model

import com.nomargin.gptextapp.util.model.AnswerModel
import com.nomargin.gptextapp.util.model.NewAnswerModel
import com.nomargin.gptextapp.util.model.NewSendMessageModel
import com.nomargin.gptextapp.util.model.SendMessageModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GPTEndpoint {

    @POST("chat/completions")
    fun getRetrofitAnswer(@Body message: SendMessageModel): Call<AnswerModel>

    @POST("ask")
    fun newGetRetrofitAnswer(@Body message: NewSendMessageModel): Call<NewAnswerModel>

}