package com.nomargin.gptextapp.util.model

import com.google.gson.annotations.SerializedName

data class NewSendMessageModel (
    @SerializedName("query")
    var query: String
        )