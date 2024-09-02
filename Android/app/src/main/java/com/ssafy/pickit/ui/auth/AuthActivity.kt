package com.ssafy.pickit.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityAuthBinding
import com.ssafy.pickit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val binding: ActivityAuthBinding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.apply {
            lifecycleOwner = this@AuthActivity
            this.authViewModel = viewModel
        }
        initButtonClickListener()

    }

    private fun initButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            MainActivity.start(this)
        }
    }
}