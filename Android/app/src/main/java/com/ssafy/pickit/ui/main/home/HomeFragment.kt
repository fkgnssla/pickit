package com.ssafy.pickit.ui.main.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.databinding.FragmentHomeBinding
import com.ssafy.pickit.databinding.FragmentMyPageBinding
import com.ssafy.pickit.ui.main.mypage.MyPageViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = this@HomeFragment
        }

        setupRecyclerView()
        setupButtonListeners()
        observeViewModel()

        val nestedScrollView = view.findViewById<NestedScrollView>(R.id.nestedScrollView)
        val contentAreaWrapper = view.findViewById<ConstraintLayout>(R.id.contentAreaWrapper)

        contentAreaWrapper.post {
            contentAreaWrapper.minimumHeight = nestedScrollView.height
        }
    }

    private fun setupRecyclerView() {
        val adapter = HorizontalAdapter(requireContext())
        binding.recyclerOngoingVote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerOngoingVote.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            items?.let { adapter.submitList(it) }
        }
    }

    private fun setupButtonListeners() {
        binding.btnChannel1.setOnClickListener { viewModel.onButtonClick(1) }
        binding.btnChannel2.setOnClickListener { viewModel.onButtonClick(2) }
        binding.btnChannel3.setOnClickListener { viewModel.onButtonClick(3) }
        binding.btnChannel4.setOnClickListener { viewModel.onButtonClick(4) }
        binding.btnChannel5.setOnClickListener { viewModel.onButtonClick(5) }
        binding.btnChannel6.setOnClickListener { viewModel.onButtonClick(6) }
        binding.btnChannel7.setOnClickListener { viewModel.onButtonClick(7) }
        binding.btnChannel8.setOnClickListener { viewModel.onButtonClick(8) }
        binding.btnChannel9.setOnClickListener { viewModel.onButtonClick(9) }
        binding.btnChannel10.setOnClickListener { viewModel.onButtonClick(10) }
        binding.btnChannel11.setOnClickListener { viewModel.onButtonClick(11) }
        binding.btnChannel12.setOnClickListener { viewModel.onButtonClick(12) }
        binding.btnChannel13.setOnClickListener { viewModel.onButtonClick(13) }
        binding.btnChannel14.setOnClickListener { viewModel.onButtonClick(14) }
        binding.btnChannel15.setOnClickListener { viewModel.onButtonClick(15) }
        binding.btnChannel16.setOnClickListener { viewModel.onButtonClick(16) }
    }

    private fun observeViewModel() {
        viewModel.buttonClicked.observe(viewLifecycleOwner) { buttonNumber ->
            buttonNumber?.let {
                // 버튼 클릭 시 Toast 메시지를 표시합니다.
                Toast.makeText(requireContext(), "Button $it clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}