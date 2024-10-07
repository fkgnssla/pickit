package com.ssafy.pickit.ui.main.result

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ssafy.pickit.R
import com.ssafy.pickit.domain.entity.VoteResultData
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
        val selectedCandidateId = intent.getStringExtra("selectedCandidateId")
        viewModel.fetchVoteResultData(voteSessionId)

        viewModel.voteResultData.observe(this) { response ->
            response?.let {
                setupHorizontalBarChart(it, selectedCandidateId)
            }
        }
    }

    private fun setupHorizontalBarChart(voteResultData: VoteResultData, selectedCandidateId: String?) {
        val entries = mutableListOf<BarEntry>()
        val colors = mutableListOf<Int>()
        val candidateNames = mutableListOf<String>()

        voteResultData.results.forEachIndexed { index, candidateResult ->

            entries.add(BarEntry(index.toFloat(), candidateResult.voteCount.toFloat()))
            candidateNames.add(candidateResult.candidateName)

            if (selectedCandidateId != null && candidateResult.candidateId == selectedCandidateId) {
                colors.add(Color.RED)
            } else {
                colors.add(if (candidateResult.isVote) Color.GRAY else Color.parseColor("#ADD8E6"))
            }
        }

        val dataSet = BarDataSet(entries, "투표 결과").apply {
            setColors(colors)
            valueTextColor = Color.BLACK
            valueTextSize = 16f
            setDrawValues(true)

        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.5f // 바의 폭 조정
        }

        horizontalBarChart.apply {
            data = barData
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = true
            description.isEnabled = false
            legend.isEnabled = false


            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    val index = value.toInt()
                    return if (index in candidateNames.indices) {
                        candidateNames[index]
                    } else {
                        ""
                    }
                }
            }

            data.setValueFormatter(object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            })

            axisLeft.axisMinimum = 0f
            axisRight.axisMinimum = 0f
            axisRight.axisMaximum = voteResultData.results.maxOf { it.voteCount.toFloat() } + 30f



            xAxis.axisMinimum = -0.5f
            xAxis.axisMaximum = entries.size.toFloat()
            xAxis.granularity = 1f

            xAxis.setDrawGridLines(false)

            val chartHeight = (voteResultData.results.size * 100).coerceAtLeast(400)
            layoutParams.height = chartHeight.toInt()
            requestLayout()

            invalidate()
        }
    }

}
