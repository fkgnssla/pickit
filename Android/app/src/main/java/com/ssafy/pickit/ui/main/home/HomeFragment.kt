package com.ssafy.pickit.ui.main.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.databinding.FragmentHomeBinding
import com.ssafy.pickit.databinding.FragmentMyPageBinding
import com.ssafy.pickit.ui.main.mypage.MyPageViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = this@HomeFragment
        }
    }
}