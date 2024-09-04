package com.ssafy.pickit.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
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

        var keyHash = Utility.getKeyHash(this)
        Log.d("kakaoHash", keyHash)

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

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("kakaoLogin", "카카오계정 로그인 성공 ${token.accessToken}")
            // 사용자 정보 가져오기
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.d("kakaoLogin", "카카오계정 사용자 정보 가져오기 실패")
                } else if (user != null) {
                    user.id
                    Log.d(
                        "kakaoLogin",
                        "카카오계정 사용자 정보 가져오기 성공\n" +
                                "닉네임 = ${user.kakaoAccount?.profile?.nickname}\n " +
                                "프사 : ${user.kakaoAccount?.profile?.profileImageUrl}\n" +
                                "이메일 : ${user.kakaoAccount?.email}\n" +
                                "아이디 : ${user.id}\n" +
                                "이름 : ${user.kakaoAccount?.name}"
                    )
                }
            }
            MainActivity.start(this)
        } else if (error != null) {
            Log.d("kakaoLogin", error.toString())
        }
    }


    private fun initButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            getKaKaoToken(kakaoLoginCallback)

        }
    }
}