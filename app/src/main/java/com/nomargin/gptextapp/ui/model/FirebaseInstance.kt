package com.nomargin.gptextapp.ui.model

import com.google.firebase.auth.FirebaseAuth

class FirebaseInstance private constructor(){

    companion object{
        private lateinit var FIREBASE_AUTHENTICATION: FirebaseAuth
        fun getFirebaseAuthentication(): FirebaseAuth{
            if(!::FIREBASE_AUTHENTICATION.isInitialized){
                FIREBASE_AUTHENTICATION = FirebaseAuth.getInstance()
            }
            return FIREBASE_AUTHENTICATION
        }
    }

}