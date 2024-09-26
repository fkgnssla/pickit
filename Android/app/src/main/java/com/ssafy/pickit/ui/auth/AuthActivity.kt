package com.ssafy.pickit.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
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
<<<<<<< HEAD
        initViewModelObserve()
    }

    private fun initViewModelObserve() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is AuthViewModel.LoginState.OldUserState -> {
                    finish()
                    MainActivity.start(this)
                }

                is AuthViewModel.LoginState.NewUserState -> {
                    finish()
                    RegisterActivity.start(this)
                }

                is AuthViewModel.LoginState.LoadingState -> {
                }
            }
        }
=======
>>>>>>> develop
    }

    //TODO : util 함수로 분리할 것(activity context 넘기는 방식으로)
    private fun getKaKaoToken(callback: (OAuthToken?, Throwable?) -> Unit) {
        val isKakaoLoginAvailable = UserApiClient.instance.isKakaoTalkLoginAvailable(this)
        if (isKakaoLoginAvailable) {
            UserApiClient.instance.loginWithKakaoTalk(
                this,
                callback = callback
            )
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = callback
            )
        }
    }

<<<<<<< HEAD
=======
    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            // 사용자 정보 가져오기
            MainActivity.start(this)
        } else if (error != null) {
            Log.d("kakaoLogin", error.toString())
        }
    }


>>>>>>> develop
    private fun initButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            getKaKaoToken(viewModel.kakaoLoginCallback)
        }
    }
}