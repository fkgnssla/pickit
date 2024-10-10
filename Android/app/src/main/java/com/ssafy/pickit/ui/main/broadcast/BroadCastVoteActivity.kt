package com.ssafy.pickit.ui.main.broadcast

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityBroadcastVoteBinding
import com.ssafy.pickit.ui.main.vote.BroadCastCompletedAdapter
import com.ssafy.pickit.ui.main.vote.BroadCastInProgressAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BroadCastVoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBroadcastVoteBinding
    private val viewModel: BroadCastVoteViewModel by viewModels()

    private val inProgressAdapter: BroadCastInProgressAdapter by lazy { BroadCastInProgressAdapter(this) }
    private val completedAdapter: BroadCastCompletedAdapter by lazy { BroadCastCompletedAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        val broadcastId = intent.getStringExtra("BROADCAST_ID") ?: ""

        viewModel.setBroadcastId(broadcastId)



        binding.recyclerView.layoutManager = LinearLayoutManager(this)



        viewModel.onInProgressButtonClicked(broadcastId)
        binding.buttonInProgress.setOnClickListener {

            viewModel.onInProgressButtonClicked(broadcastId)
        }

        binding.buttonCompleted.setOnClickListener {

            viewModel.onCompletedButtonClicked(broadcastId)
        }


        observeViewModel()
    }




    private fun observeViewModel() {
        viewModel.stationName.observe(this) { stationName ->
            Log.d("BroadCastVoteActivity", "Data received: $stationName")
            binding.tabTitle.text = stationName
        }

        viewModel.voteData.observe(this) { data ->
            if (data.isNotEmpty()) {
                Log.d("BroadCastVoteActivity", "Data received: $data")
                if (viewModel.isInProgressSelected.value == true) {
                    inProgressAdapter.submitList(data)
                    binding.recyclerView.adapter = inProgressAdapter
                } else {
                    completedAdapter.submitList(data)
                    binding.recyclerView.adapter = completedAdapter
                }
            } else {
                Log.d("BroadCastVoteActivity", "No data received")
            }
        }

        viewModel.isInProgressSelected.observe(this) { isInProgressSelected ->
            updateButtonStates(isInProgressSelected)
            Log.d("BroadCastVoteActivity", "isInProgressSelected changed: $isInProgressSelected")
            if (isInProgressSelected) {
                inProgressAdapter.submitList(viewModel.voteData.value)
                binding.recyclerView.adapter = inProgressAdapter
            } else {
                completedAdapter.submitList(viewModel.voteData.value)
                binding.recyclerView.adapter = completedAdapter
            }
        }

    }

    private fun updateButtonStates(isInProgressSelected: Boolean) {
        Log.d("BroadCastVoteActivity", "Updating button states: isInProgressSelected = $isInProgressSelected")

        binding.buttonInProgress.isSelected = isInProgressSelected
        binding.buttonCompleted.isSelected = !isInProgressSelected

        if (isInProgressSelected) {
            binding.buttonInProgress.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.buttonCompleted.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
        } else {
            binding.buttonInProgress.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
            binding.buttonCompleted.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }
}
