package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class SendMessageModel(
    @SerializedName("model")
    var model: String,

    @SerializedName("messages")
    var messages: ArrayList<SendMessageModel>
)
