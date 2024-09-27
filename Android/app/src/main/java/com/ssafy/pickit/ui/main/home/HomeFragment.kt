package com.ssafy.pickit.ui.main.home

import ChannelButtonAdapter
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.data.datasource.remote.response.vote.CandidateResult
import com.ssafy.pickit.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var pieChart: PieChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = this@HomeFragment
        }

        setupRecyclerView()
        setupChannelButtonsRecyclerView()
        observeViewModel()

        val nestedScrollView = view.findViewById<NestedScrollView>(R.id.nestedScrollView)
        val contentAreaWrapper = view.findViewById<ConstraintLayout>(R.id.contentAreaWrapper)

        contentAreaWrapper.post {
            contentAreaWrapper.minimumHeight = nestedScrollView.height
        }

        pieChart = binding.pieChart
        observeVoteResultData()
    }

    private fun setupRecyclerView() {
        val adapter = HorizontalAdapter(requireContext())
        binding.recyclerOngoingVote.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerOngoingVote.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            items?.let { adapter.submitList(it) }
        }
    }

    private fun setupChannelButtonsRecyclerView() {
        val adapter = ChannelButtonAdapter(emptyList()) { id ->
            viewModel.onButtonClick(id)
        }

        binding.recyclerChannelButtons.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 4)
        }

        viewModel.channelButtons.observe(viewLifecycleOwner) { buttons ->
            adapter.updateItems(buttons ?: emptyList())
        }
    }

    private fun observeViewModel() {
        viewModel.buttonClicked.observe(viewLifecycleOwner) { buttonNumber ->
            buttonNumber?.let {
                Toast.makeText(requireContext(), "Button $it clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeVoteResultData() {
        viewModel.voteResultResponse.observe(viewLifecycleOwner) { voteResult ->
            voteResult?.let {
                updatePieChart(it.results)
            }
        }
    }

    private fun updatePieChart(results: List<CandidateResult>) {
        val entries = results.map { PieEntry(it.voteCount.toFloat(), it.candidateName) }
        val dataSet = PieDataSet(entries, "투표 결과").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
        }

        val pieData = PieData(dataSet)
        pieChart.data = pieData

    }
}