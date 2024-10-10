package com.ssafy.pickit.ui.main.result

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityResultBinding
import com.ssafy.pickit.databinding.ActivityVoteDetailBinding
import com.ssafy.pickit.domain.entity.CandidateResultData
import com.ssafy.pickit.domain.entity.VoteResultData
import com.ssafy.pickit.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var candidateLayout: LinearLayout
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        candidateLayout = binding.candidateLayout


        val selectedCandidateId = intent.getStringExtra("selectedCandidateId")
        val voteSessionId = intent.getStringExtra("voteSessionId") ?: return

        viewModel.fetchVoteSessionData(voteSessionId)
        viewModel.fetchVoteResultData(voteSessionId)

        viewModel.voteSessionResponse.observe(this) { response ->
            response?.let {
                binding.textViewTitle.text = it.title
                Glide.with(this)
                    .load(it.thumbnail)
                    .into(binding.imageView)
            }
        }

        viewModel.voteResultData.observe(this) { response ->
            response?.let {
                displayCandidates(it, selectedCandidateId)
            }
        }
    }

    private fun displayCandidates(voteResultData: VoteResultData,selectedCandidateId: String?) {


        candidateLayout.removeAllViews()

        voteResultData.results.forEach { candidateResult ->

            val inflater = LayoutInflater.from(this)
            val candidateView = inflater.inflate(R.layout.item_candidate, candidateLayout, false)

            val candidateChart: HorizontalBarChart = candidateView.findViewById(R.id.horizontalBarChart)
            setupHorizontalBarChart(candidateChart, candidateResult, voteResultData, candidateResult.voteCount,selectedCandidateId)


            val candidateNameTextView = candidateView.findViewById<TextView>(R.id.candidateName)
            candidateNameTextView.text = candidateResult.candidateName

            val candidateImageView = candidateView.findViewById<ImageView>(R.id.candidateImage)
            Glide.with(this).load(candidateResult.profileImage).into(candidateImageView)

            candidateLayout.addView(candidateView)
        }
    }

    private fun setupHorizontalBarChart(
        horizontalBarChart: HorizontalBarChart,
        candidateResult: CandidateResultData,
        voteResultData: VoteResultData,
        voteCount: Long,
        selectedCandidateId: String?
    ) {
//        val entries = mutableListOf<BarEntry>().apply {
//            add(BarEntry(voteCount.toFloat(), candidateResult.voteCount.toFloat()))
//        }
        val entries = mutableListOf<BarEntry>()
        val colors = mutableListOf<Int>()


        entries.add(BarEntry(0f, candidateResult.voteCount.toInt().toFloat()))

        if (candidateResult.candidateId == selectedCandidateId) {
            colors.add(R.color.pickit_pink)
        } else {
            colors.add(if (candidateResult.isVote) R.color.pickit_pink else Color.parseColor("#ADD8E6"))
        }

        val dataSet = BarDataSet(entries, "").apply {
            setColors(colors)
            valueTextColor = Color.BLACK
            valueTextSize = 16f
            setDrawValues(true)
            valueFormatter = object : ValueFormatter() {
                override fun getBarLabel(barEntry: BarEntry?): String {
                    return barEntry?.y?.toInt().toString()
                }
            }
        }


        val barData = BarData(dataSet).apply {
            barWidth = 0.5f
        }

        horizontalBarChart.apply {
            data = barData
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            description.isEnabled = false
            legend.isEnabled = false


            axisLeft.valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return ""
                }
            }

            axisLeft.axisMinimum = 0f // 최소값
            axisLeft.axisMaximum = voteResultData.results.maxOf { it.voteCount.toFloat() } * 1.1f

            invalidate()
        }
    }

}