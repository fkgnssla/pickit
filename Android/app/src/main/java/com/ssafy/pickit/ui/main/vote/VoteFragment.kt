package com.ssafy.pickit.ui.main.vote

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.databinding.FragmentVoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoteFragment : BaseFragment<FragmentVoteBinding>(R.layout.fragment_vote) {
    private val viewModel: VoteViewModel by viewModels()

    private val inProgressAdapter: InProgressAdapter by lazy { InProgressAdapter(this) }
    private val completedAdapter = CompletedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            recyclerView.layoutManager = LinearLayoutManager(context)

        }

        viewModel.voteData.observe(viewLifecycleOwner) { data ->
            Log.d("VoteFragment", "Data received: $data")
            if (viewModel.isInProgressSelected.value == true) {
                Log.d("VoteFragment", "InProgress button selected")
                inProgressAdapter.submitList(data)
                binding.recyclerView.adapter = inProgressAdapter
            } else {
                Log.d("VoteFragment", "Completed button selected")
                completedAdapter.submitList(data)
                binding.recyclerView.adapter = completedAdapter
            }
        }

        viewModel.isInProgressSelected.observe(viewLifecycleOwner) { isInProgressSelected ->
            updateButtonStates(isInProgressSelected)
        }


    }

    private fun updateButtonStates(isInProgressSelected: Boolean) {
        Log.d("VoteFragment", "Updating button states: isInProgressSelected = $isInProgressSelected")


        binding.buttonInProgress.isSelected = isInProgressSelected
        binding.buttonCompleted.isSelected = !isInProgressSelected


        if (isInProgressSelected) {
            binding.buttonInProgress.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.buttonCompleted.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
        } else {
            binding.buttonInProgress.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
            binding.buttonCompleted.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }


        binding.buttonInProgress.setOnClickListener {
            viewModel.onInProgressButtonClicked()
        }

        binding.buttonCompleted.setOnClickListener {
            viewModel.onCompletedButtonClicked()
        }
    }

}
