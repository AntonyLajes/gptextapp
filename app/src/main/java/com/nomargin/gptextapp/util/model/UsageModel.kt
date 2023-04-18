package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class UsageModel(
    @SerializedName("prompt_tokens")
    var prompt_tokens: String,

    @SerializedName("completion_tokens")
    var completion_tokens: String,

    @SerializedName("total_tokens")
    var total_tokens: String
)
