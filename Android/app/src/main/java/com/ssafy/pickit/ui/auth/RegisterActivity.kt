package com.ssafy.pickit.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityRegisterBinding
import com.ssafy.pickit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    lateinit var viewmodel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.apply {
            lifecycleOwner = this@RegisterActivity
            this.registerViewModel = viewmodel
        }
        initButtonListener()
        initObserve()
    }

    private fun initButtonListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                viewmodel.register()
            }
            btnCreateWallet.setOnClickListener {
                viewmodel.generateWallet()
            }
        }
    }

    private fun initObserve() {
        viewmodel.walletState.observe(this) { state ->
            when (state) {
                is RegisterViewModel.ApiState.SuccessState -> {
                    Toast.makeText(this, "지갑 생성 완료", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.FailState -> {
                    Toast.makeText(this, "지갑 생성 실패", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.LoadingState -> {
                    // 로딩 상태일 때 처리
                    Toast.makeText(this, "지갑 생성 중...", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.DefaultState ->{}
            }
        }

        viewmodel.registerState.observe(this) { state ->
            when (state) {
                is RegisterViewModel.ApiState.SuccessState -> {
                    Toast.makeText(this, "정보 등록 완료", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.FailState -> {
                    Toast.makeText(this, "정보 등록 실패", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.LoadingState -> {
                    // 로딩 상태일 때 처리
                    Toast.makeText(this, "지갑 생성 중...", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.DefaultState ->{}
            }
        }
    }

    companion object {
        fun start(context : Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}