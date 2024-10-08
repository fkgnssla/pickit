package com.ssafy.pickit.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by viewModels()
    private lateinit var adapter: MyPageViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = this@MyPageFragment
        }

        initViewPager()
    }

    private fun initViewPager() {
        adapter = MyPageViewPagerAdapter(this)
        binding.vpMyVote.adapter = adapter

        val list = listOf("진행중", "완료")

        TabLayoutMediator(binding.tlMyVote, binding.vpMyVote) { tab, position ->
            tab.text = list.get(position)
        }.attach()
    }
}

