package com.ssafy.pickit.ui.auth

import android.os.Bundle
import android.util.Log
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
        initViewModelObserve()

        /* 작업 환경 변경시 해당 해시값 카카오 디벨로퍼에 등록
        var keyHash = Utility.getKeyHash(this)
        Log.d("kakaoHash", keyHash)
         */

    }

    private fun initViewModelObserve() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is AuthViewModel.LoginState.OldUserState -> {
                    MainActivity.start(this)
                }

                is AuthViewModel.LoginState.NewUserState -> {
                    RegisterActivity.start(this)
                }

                is AuthViewModel.LoginState.LoadingState -> {
                }
            }
        }
    }

    //TODO : util 함수로 분리할 것(activity context 넘기는 방식으로)
    private fun getKaKaoToken(callback: (OAuthToken?, Throwable?) -> Unit) {
        val isKakaoLoginAvailable = UserApiClient.instance.isKakaoTalkLoginAvailable(this)
        if (isKakaoLoginAvailable) {
            Log.d("kakaoLogin", "카카오톡으로 로그인 가능")
            UserApiClient.instance.loginWithKakaoTalk(
                this,
                callback = callback
            )
        } else {
            Log.d("kakaoLogin", "카카오톡으로 로그인 불가능")
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = callback
            )
        }
    }

    private fun initButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            getKaKaoToken(viewModel.kakaoLoginCallback)
        }
    }
}