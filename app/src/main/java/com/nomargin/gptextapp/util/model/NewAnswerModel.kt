package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class NewAnswerModel (
    @SerializedName("conversationId")
    var conversationId: String,

    @SerializedName("response")
    var response: String
        )