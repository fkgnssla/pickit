package com.ssafy.pickit.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.pickit.R

import com.ssafy.pickit.data.datasource.remote.response.vote.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteSessionResponse

import com.ssafy.pickit.ui.main.channel.ChannelButton

class HomeViewModel : ViewModel() {

    private val _buttonClicked = MutableLiveData<Int>()
    val buttonClicked: LiveData<Int> get() = _buttonClicked

    private val _items = MutableLiveData<List<Int>>()
    val items: LiveData<List<Int>> get() = _items

    private val _channelButtons = MutableLiveData<List<ChannelButton>>()
    val channelButtons: LiveData<List<ChannelButton>> get() = _channelButtons


    private val _voteSessionResponse = MutableLiveData<VoteSessionResponse?>()
    val voteSessionResponse: MutableLiveData<VoteSessionResponse?> get() = _voteSessionResponse

    private val _voteResultResponse = MutableLiveData<VoteResultResponse>()
    val voteResultResponse: LiveData<VoteResultResponse> get() = _voteResultResponse

    init {
        _items.value = listOf(
            R.drawable.ic_vote2,
            R.drawable.ic_vote1,
            R.drawable.ic_vote3,
            R.drawable.ic_vote5
        )


        _channelButtons.value = listOf(
            ChannelButton("1", R.drawable.ic_channel_1),
            ChannelButton("2", R.drawable.ic_channel_2),
            ChannelButton("3", R.drawable.ic_channel_3),
            ChannelButton("4", R.drawable.ic_channel_4),
            ChannelButton("5", R.drawable.ic_channel_5),
            ChannelButton("6", R.drawable.ic_channel_6),
            ChannelButton("7", R.drawable.ic_channel_7),
            ChannelButton("8", R.drawable.ic_channel_8),
            ChannelButton("9", R.drawable.ic_channel_9),
            ChannelButton("10", R.drawable.ic_channel_10),
            ChannelButton("11", R.drawable.ic_channel_11),
            ChannelButton("12", R.drawable.ic_channel_12),
            ChannelButton("13", R.drawable.ic_channel_13),
            ChannelButton("14", R.drawable.ic_channel_14),
            ChannelButton("15", R.drawable.ic_channel_15),
            ChannelButton("16", R.drawable.ic_channel_16)
        )

        // setExampleVoteResultData()
    }

    fun onButtonClick(buttonNumber: Int) {
        _buttonClicked.value = buttonNumber
    }



//    private fun setExampleVoteResultData() {
//        val exampleResults = (1..3).map { index ->
//            CandidateResult(
//                candidateId = index.toString(),
//                candidateName = "후보자 $index",
//                voteCount = (Math.random() * 300).toLong()
//            )
//        }
//
//
//
//
//        val exampleVoteSession = VoteSessionResponse(
//            id = "1",
//            title = "예시 투표",
//            description = "투표 설명",
//            thumbnail = "https://via.placeholder.com/150",
//            candidates = exampleResults.map { CandidateResponse(it.candidateName, "", it.voteCount) }, // Candidate로 변환
//            startDate = "2024-09-27T00:00:00",
//            endDate = "2024-09-28T00:00:00"
//        )
//
//
//        _voteSessionResponse.value = exampleVoteSession
//
//
//        val voteResultResponse = VoteResultResponse(
//            voteSession = exampleVoteSession,
//            results = exampleResults
//        )
//
//
//        _voteResultResponse.value = voteResultResponse
//    }
}