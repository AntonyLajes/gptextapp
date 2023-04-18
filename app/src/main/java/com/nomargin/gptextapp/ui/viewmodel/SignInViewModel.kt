package com.nomargin.gptextapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.nomargin.gptextapp.ui.model.FirebaseInstance
import com.nomargin.gptextapp.util.model.StatusModel

class SignInViewModel(application: Application): AndroidViewModel(application) {

    private val firebaseAuthentication = FirebaseInstance.getFirebaseAuthentication()
    private var _googleSignInStatus: MutableLiveData<StatusModel> = MutableLiveData()
    val googleSignInStatus: LiveData<StatusModel> get() = _googleSignInStatus
    private var _emailSignInStatus: MutableLiveData<StatusModel> = MutableLiveData()
    val emailSignInStatus: LiveData<StatusModel> get() = _emailSignInStatus

    fun googleSignInCredential(googleSignInCredential: AuthCredential) {
        firebaseAuthentication.signInWithCredential(googleSignInCredential).addOnCompleteListener { googleSignInTask ->
            if(googleSignInTask.isSuccessful){
                _googleSignInStatus.value = StatusModel()
            }else{
                _googleSignInStatus.value = StatusModel(false, googleSignInTask.exception?.message.toString())
            }
        }
    }

    fun emailSignIn(context: Context, email: String, password: String){
        firebaseAuthentication.signInWithEmailAndPassword(email, password).addOnCompleteListener { emailSignInTask ->
            if(emailSignInTask.isSuccessful){
                _emailSignInStatus.value = StatusModel()
            }else{
                _emailSignInStatus.value = StatusModel(false, emailSignInTask.exception?.message.toString())
            }
        }
    }

}