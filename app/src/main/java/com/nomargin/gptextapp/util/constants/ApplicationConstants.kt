package com.nomargin.gptextapp.util.constants

class ApplicationConstants private constructor(){

    companion object{
        object MessageType{
            const val MESSAGE_SENDED = "sended"
            const val MESSAGE_RECEIVED = "received"
        }
        object UserData{
            const val USER_NAME = "name"
            const val USER_EMAIL = "email"
        }
    }

}