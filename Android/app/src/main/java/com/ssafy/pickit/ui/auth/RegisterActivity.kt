package com.ssafy.pickit.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityRegisterBinding
import com.ssafy.pickit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.apply {
            lifecycleOwner = this@RegisterActivity
            this.registerViewModel = viewModel
        }

        initObserve()

    }

    private fun initObserve() {

        viewModel.walletState.observe(this) { state ->
            when (state) {
                is RegisterViewModel.ApiState.SuccessState -> {
                    Toast.makeText(this, "지갑 생성 완료", Toast.LENGTH_SHORT).show()
                    viewModel.register()
                }
                is RegisterViewModel.ApiState.FailState -> {
                    Toast.makeText(this, "지갑 생성 실패", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.LoadingState -> {
                    Toast.makeText(this, "지갑 생성 중...", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.DefaultState -> {}
            }
        }


        viewModel.registerState.observe(this) { state ->
            when (state) {
                is RegisterViewModel.ApiState.SuccessState -> {
                    Toast.makeText(this, "정보 등록 완료", Toast.LENGTH_SHORT).show()
                    MainActivity.start(this)
                }
                is RegisterViewModel.ApiState.FailState -> {
                    Toast.makeText(this, "정보 등록 실패", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.LoadingState -> {
                    Toast.makeText(this, "정보 등록 중...", Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.ApiState.DefaultState -> {}
            }
        }

        viewModel.isFormValid.observe(this) { isValid ->
            binding.saveButton.isEnabled = isValid
        }

        viewModel.showToastEvent.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}
