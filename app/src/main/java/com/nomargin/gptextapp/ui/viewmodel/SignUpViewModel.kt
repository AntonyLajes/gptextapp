package com.nomargin.gptextapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.ui.model.FirebaseInstance
import com.nomargin.gptextapp.util.model.StatusModel

class SignUpViewModel(application: Application): AndroidViewModel(application) {

    private val firebaseAuthentication = FirebaseInstance.getFirebaseAuthentication()
    private var _createUserStatus: MutableLiveData<StatusModel> = MutableLiveData()
    val createUserStatus: LiveData<StatusModel> get() = _createUserStatus

    fun createUser(context: Context,email: String, password: String){
        firebaseAuthentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener { createUserTask ->
            if(createUserTask.isSuccessful){
                _createUserStatus.value = StatusModel(message = context.getString(R.string.user_created))
            }else{
                _createUserStatus.value = StatusModel(false, createUserTask.exception?.message.toString())
            }
        }
    }

}