package com.ssafy.pickit.ui.main.vote

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.databinding.FragmentMyPageBinding
import com.ssafy.pickit.databinding.FragmentVoteBinding
import com.ssafy.pickit.ui.main.mypage.MyPageViewModel

class VoteFragment : BaseFragment<FragmentVoteBinding>(R.layout.fragment_vote) {
    private val viewModel: VoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = this@VoteFragment
        }
    }
}