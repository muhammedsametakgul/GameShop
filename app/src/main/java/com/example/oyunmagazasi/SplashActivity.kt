package com.example.oyunmagazasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = object: CountDownTimer(1600, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
            val intent=Intent(this@SplashActivity,AuthActivity::class.java)
                startActivity(intent)

            }
        }
        timer.start()
    }
}