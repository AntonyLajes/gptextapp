package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class ReceiveMessageModel(
    @SerializedName("message")
    var message: MessageModel,

    @SerializedName("finish_reason")
    var finish_reason: String,

    @SerializedName("index")
    var index: Int,
)
