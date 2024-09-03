package com.ssafy.pickit.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
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

        // 페이지 변경 애니메이션이 자연스러워지도록 스크롤 설정 조정
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 필요시 페이지 전환 시 추가 로직을 여기에 작성
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
    }

    private fun initButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
