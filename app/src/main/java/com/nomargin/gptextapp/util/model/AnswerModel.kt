package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class AnswerModel(
    @SerializedName("id")
    var id: String,

    @SerializedName("object")
    var answerObject: String,

    @SerializedName("created")
    var created: String,

    @SerializedName("model")
    var model: String,

    @SerializedName("usage")
    var usage: UsageModel,

    @SerializedName("choices")
    var choices: ArrayList<ReceiveMessageModel>
)
