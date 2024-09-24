package com.ssafy.pickit.ui.main.result

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.ssafy.pickit.R
import com.ssafy.pickit.data.datasource.remote.response.VoteResultResponse
import com.ssafy.pickit.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    private lateinit var horizontalBarChart: HorizontalBarChart
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        horizontalBarChart = findViewById(R.id.horizontalBarChart)

        val voteSessionId = intent.getStringExtra("voteSessionId") ?: return
        viewModel.fetchVoteResultData(voteSessionId)

        viewModel.voteResultResponse.observe(this) { response ->
            response?.let {
                setupHorizontalBarChart(it)
            }
        }
    }

    private fun setupHorizontalBarChart(voteResultResponse: VoteResultResponse) {
        val entries = mutableListOf<BarEntry>()
        val colors = mutableListOf<Int>()
        val selectedCandidateId = viewModel.selectedCandidateId.value


        voteResultResponse.results.forEachIndexed { index, candidateResult ->
            entries.add(BarEntry(index.toFloat() * 3, candidateResult.voteCount.toFloat()))


            if (candidateResult.candidateId == selectedCandidateId) {
                colors.add(Color.RED)
            } else {
                colors.add(Color.parseColor("#ADD8E6"))
            }
        }


        val dataSet = BarDataSet(entries, "투표 결과").apply {
            setColors(colors)
            valueTextColor = Color.BLACK
            valueTextSize = 16f
            setDrawValues(true)
        }

        val barData = BarData(dataSet).apply {
            barWidth = 1.5f
        }

        horizontalBarChart.apply {
            data = barData
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            description.isEnabled = false
            legend.isEnabled = false

            axisLeft.axisMinimum = 0f
            axisRight.axisMinimum = 0f
            axisRight.axisMaximum = voteResultResponse.results.maxOf { it.voteCount.toFloat() } + 30f

            xAxis.axisMinimum = -0.5f
            xAxis.axisMaximum = entries.size.toFloat() * 3 - 0.5f
            xAxis.granularity = 1f

            val chartHeight = (voteResultResponse.results.size * 100).coerceAtLeast(400)
            layoutParams.height = chartHeight.toInt()
            requestLayout()

            invalidate()
        }
    }

}
