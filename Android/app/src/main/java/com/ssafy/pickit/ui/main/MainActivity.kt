package com.ssafy.pickit.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityMainBinding
import com.ssafy.pickit.ui.main.home.HomeFragment
import com.ssafy.pickit.ui.main.mypage.MyPageFragment
import com.ssafy.pickit.ui.main.vote.VoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }

        setFragment()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

    }

    private fun setFragment() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, HomeFragment())
            .commit()

        binding.bnvMain.selectedItemId = R.id.home

        binding.bnvMain.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.home -> {
                    transaction.replace(R.id.fcv_main, HomeFragment())
                }

                R.id.vote -> {
                    transaction.replace(R.id.fcv_main, VoteFragment())
                }

                R.id.my_vote -> {
                    transaction.replace(R.id.fcv_main, MyPageFragment())
                }
            }
            transaction.commit()
            return@setOnItemSelectedListener true
        }
    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}