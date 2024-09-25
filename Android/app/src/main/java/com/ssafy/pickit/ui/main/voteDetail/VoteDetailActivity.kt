// File: VoteDetailActivity.kt
package com.ssafy.pickit.ui.main.voteDetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ssafy.pickit.R
import com.ssafy.pickit.data.datasource.remote.response.Candidate
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import com.ssafy.pickit.databinding.ActivityVoteDetailBinding
import com.ssafy.pickit.databinding.DialogCandidateBinding
import com.ssafy.pickit.databinding.GridItemLayoutBinding
import com.ssafy.pickit.ui.main.result.ResultActivity
//import com.ssafy.pickit.ui.main.result.ResultActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class VoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoteDetailBinding
    private val viewModel: VoteDetailViewModel by viewModels()
    private var selectedCandidate: Candidate? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val voteSession: VoteSessionResponse? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableExtra("voteSession", VoteSessionResponse::class.java)
//        } else {
//            intent.getParcelableExtra("voteSession") as? VoteSessionResponse
//        }
//
//        voteSession?.let {
//            viewModel.setVoteSessionResponse(it)
//        }
        val voteSessionId= intent.getStringExtra("voteSessionId")
        if (voteSessionId != null) {
            viewModel.fetchVoteSessionData(voteSessionId)
        }



        viewModel.voteSessionResponse.observe(this) { response ->
            response?.let {
                binding.textViewTitle.text = it.title
                binding.textViewStartDate.text = it.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                binding.textViewEndDate.text = it.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                Glide.with(this)
                    .load(it.thumbnail)
                    .into(binding.imageView)
                loadItemsToGrid(it)
            }
        }

        binding.buttonSubmit.setOnClickListener {
            onSubmitClicked()
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
        val voteSessionId = viewModel.voteSessionResponse.value?.id
        if (voteSessionId != null) {
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("voteSessionId", voteSessionId)
            }
            startActivity(intent)
            finish() // Close the current activity
        }
    }

    private fun showCandidateDialog(selectedCandidate: Candidate) {
        val dialogBinding = DialogCandidateBinding.inflate(layoutInflater)

        val customDialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()


        Glide.with(this)
            .load(selectedCandidate.profile_img)
            .into(dialogBinding.dialogImageView)
        dialogBinding.dialogNameTextView.text = selectedCandidate.name


        dialogBinding.buttonCancel.setOnClickListener {
            customDialog.dismiss()
        }

        dialogBinding.buttonConfirm.setOnClickListener {
            val voteSessionId = viewModel.voteSessionResponse.value?.id
            if (voteSessionId != null) {
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("voteSessionId", voteSessionId)
                }
                startActivity(intent)
            }
            customDialog.dismiss()
        }

        customDialog.show()
    }


    private fun loadItemsToGrid(voteSessionResponse: VoteSessionResponse) {
        val gridLayout = binding.gridLayoutImages
        gridLayout.removeAllViews()

        voteSessionResponse.candidates.forEach { candidate ->
            val itemBinding = GridItemLayoutBinding.inflate(layoutInflater, gridLayout, false)

            Glide.with(this)
                .load(candidate.profile_img)
                .into(itemBinding.imageView)

            itemBinding.textViewName.text = candidate.name

            itemBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (selectedCandidate != null) {
                        // 기존 선택된 후보자 제거
                        val previouslySelectedCheckbox = gridLayout.findViewWithTag<CheckBox>(selectedCandidate?.name)
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