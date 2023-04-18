package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("role")
    val role: String,

    @SerializedName("content")
    val content: String
)
