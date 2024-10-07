package com.ssafy.pickit.ui.auth

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
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
            this.authViewModel = viewModel
        }
        setupViewPager()
        initButtonClickListener()
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
                    AgreementActivity.start(this)
                }
                is AuthViewModel.LoginState.LoadingState -> {
                }
            }
        }
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        adapter = ServicePageAdapter(this)
        viewPager.adapter = adapter

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

    }

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

    private fun initButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            getKaKaoToken(viewModel.kakaoLoginCallback)
        }
    }
}