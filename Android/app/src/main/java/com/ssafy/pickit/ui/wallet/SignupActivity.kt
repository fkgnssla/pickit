package com.ssafy.pickit.ui.wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.pickit.R

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignupFragment())

                .commit()
        }
    }
}
