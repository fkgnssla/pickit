package com.ssafy.pickit.ui


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.appcompat.app.AppCompatActivity
import com.ssafy.pickit.R
import com.ssafy.pickit.ui.auth.AuthActivity
import com.ssafy.pickit.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed({

            if (isUserLoggedIn()) {

                startActivity(Intent(this, MainActivity::class.java))
            } else {

                startActivity(Intent(this, AuthActivity::class.java))
            }


            finish()

        }, 2000)

    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("VotePreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }


}
