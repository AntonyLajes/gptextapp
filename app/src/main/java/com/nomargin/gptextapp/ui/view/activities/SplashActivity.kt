package com.nomargin.gptextapp.ui.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.method.LinkMovementMethod
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.databinding.ActivitySplashBinding
import com.nomargin.gptextapp.ui.model.FirebaseInstance

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.applicationCredits.setLinkTextColor(getColor(R.color.low_white))
        binding.applicationCredits.movementMethod = LinkMovementMethod.getInstance()
        Handler().postDelayed({
            if(FirebaseInstance.getFirebaseAuthentication().currentUser != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, AuthenticationActivity::class.java))
                finish()
            }
        }, 500)
    }
}