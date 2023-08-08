package com.example.textbacegamekot1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splashTimeOut: Long = 3000 //Delay in milliseconds (3seconds)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            // Apply transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }, splashTimeOut)


    }

}