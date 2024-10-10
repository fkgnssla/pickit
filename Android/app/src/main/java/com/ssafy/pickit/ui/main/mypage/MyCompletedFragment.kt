package com.ssafy.pickit.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.pickit.databinding.FragmentMyCompletedBinding
import com.ssafy.pickit.ui.main.vote.CompletedAdapter


class MyCompletedFragment : Fragment() {
    private var _binding: FragmentMyCompletedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyPageViewModel
    private lateinit var adapter: CompletedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCompletedBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireParentFragment()).get(MyPageViewModel::class.java)
        initAdapter()

        return view
    }

    private fun initAdapter() {
        binding.rvEndList.layoutManager = LinearLayoutManager(context)
        adapter = CompletedAdapter()
        binding.rvEndList.adapter = adapter

        viewModel.endMyVoteList.observe(viewLifecycleOwner) { voteList ->
            if (voteList.size == 0) {
                binding.tvEmpty.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                adapter.submitList(voteList)
                binding.tvEmpty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            }

        }
    }

}