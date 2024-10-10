package com.ssafy.pickit.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.pickit.databinding.FragmentMyInProgressBinding
import com.ssafy.pickit.ui.main.vote.CompletedAdapter
import com.ssafy.pickit.ui.main.vote.InProgressAdapter

class MyInProgressFragment : Fragment() {
    private var _binding: FragmentMyInProgressBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyPageViewModel
    private lateinit var adapter: MyPageRecyclerviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyInProgressBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireParentFragment()).get(MyPageViewModel::class.java)

        viewModel = ViewModelProvider(requireParentFragment()).get(MyPageViewModel::class.java)
        initAdapter()

        return view
    }

    private fun initAdapter() {
        binding.rvOngoingList.layoutManager= LinearLayoutManager(context)
        adapter = MyPageRecyclerviewAdapter()
        binding.rvOngoingList.adapter = adapter

        viewModel.ongoingMyVoteList.observe(viewLifecycleOwner) { voteList ->
            if (voteList.size == 0) {
                binding.tvEmpty.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            }
            adapter.submitList(voteList)

        }
    }

}