// File: VoteDetailActivity.kt
package com.ssafy.pickit.ui.main.voteDetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.ssafy.pickit.databinding.ActivityVoteDetailBinding
import com.ssafy.pickit.databinding.DialogCandidateBinding
import com.ssafy.pickit.databinding.GridItemLayoutBinding
import com.ssafy.pickit.domain.entity.CandidateData
import com.ssafy.pickit.domain.entity.VoteSessionData
import com.ssafy.pickit.ui.main.result.ResultActivity
import com.ssafy.pickit.ui.main.voteDetail.VoteDetailViewModel.VoteState
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class VoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoteDetailBinding
    private val viewModel: VoteDetailViewModel by viewModels()
    private var selectedCandidate: CandidateData? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        val voteSessionId= intent.getStringExtra("voteSessionId")

        initObserve()

        binding.buttonSubmit.setOnClickListener {
            onSubmitClicked()
        }
    }

    private fun initObserve() {
        viewModel.voteSessionResponse.observe(this) { response ->
            response?.let {
                binding.textViewTitle.text = it.title
                binding.textViewStartDate.text =
                    it.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                binding.textViewEndDate.text =
                    it.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                Glide.with(this)
                    .load(it.thumbnail)
                    .into(binding.imageView)
                loadItemsToGrid(it)
            }
        }
        viewModel.voteState.observe(this) { state ->
            when (state) {
                VoteState.SuccessState -> {
                    Toast.makeText(this, "투표에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    navigateToResultActivity()
                }
                VoteState.FailureState -> {
                    Toast.makeText(this, "투표에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                VoteState.LoadingState -> {
                    Toast.makeText(this, "투표 트랜잭션 시도중입니다.", Toast.LENGTH_SHORT).show()
                }
                VoteState.DefaultState -> {
                }
            }

        }
    }

    private fun onSubmitClicked() {
        if (selectedCandidate != null) {
            showCandidateDialog(selectedCandidate!!)
        } else {
            Toast.makeText(this, "후보자를 선택해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToResultActivity() {
        val voteSessionId = viewModel.sessionId
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("voteSessionId", voteSessionId)
        }
        startActivity(intent)
        finish()
    }

    private fun showCandidateDialog(selectedCandidate: CandidateData) {
        val dialogBinding = DialogCandidateBinding.inflate(layoutInflater)

        val customDialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        Glide.with(this)
            .load(selectedCandidate.profileImg)
            .into(dialogBinding.dialogImageView)
        dialogBinding.dialogNameTextView.text = selectedCandidate.name


        dialogBinding.buttonCancel.setOnClickListener {
            customDialog.dismiss()
        }

        dialogBinding.buttonConfirm.setOnClickListener {
            viewModel.postVote(candidateId = selectedCandidate.candidateId)
            customDialog.dismiss()
        }

        customDialog.show()
    }


    private fun loadItemsToGrid(voteSessionData: VoteSessionData) {
        val gridLayout = binding.gridLayoutImages
        gridLayout.removeAllViews()

        voteSessionData.candidates.forEach { candidate ->
            val itemBinding = GridItemLayoutBinding.inflate(layoutInflater, gridLayout, false)

            Glide.with(this)
                .load(candidate.profileImg)
                .into(itemBinding.imageView)

            itemBinding.textViewName.text = candidate.name

            itemBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (selectedCandidate != null) {

                        val previouslySelectedCheckbox =
                            gridLayout.findViewWithTag<CheckBox>(selectedCandidate?.name)
                        previouslySelectedCheckbox?.isChecked = false
                    }
                    selectedCandidate = candidate
                    itemBinding.checkBox.tag = candidate.name
                } else {
                    if (selectedCandidate == candidate) {
                        selectedCandidate = null
                    }
                }
            }

            gridLayout.addView(itemBinding.root)
        }
    }
}