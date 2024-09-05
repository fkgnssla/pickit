package com.ssafy.pickit.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityAuthBinding
import com.ssafy.pickit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ServicePageAdapter
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            lifecycleOwner = this@AuthActivity
            authViewModel = viewModel
        }

        setupViewPager()
        initButtonClickListener()
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager)
        adapter = ServicePageAdapter(this)
        viewPager.adapter = adapter

        viewPager.setOffscreenPageLimit(1)


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })

        findViewById<ImageButton>(R.id.left_arrow).setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true)
            }
        }

        findViewById<ImageButton>(R.id.right_arrow).setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1, true)
            }
        }
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
//            getKaKaoToken(kakaoLoginCallback)
            MainActivity.start(this)


        }
    }
}
