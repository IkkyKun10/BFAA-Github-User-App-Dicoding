package com.dicoding.latihan.githubuserdicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val TIME_SECOND = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            Handler(Looper.getMainLooper())
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, TIME_SECOND.toLong())
    }
}