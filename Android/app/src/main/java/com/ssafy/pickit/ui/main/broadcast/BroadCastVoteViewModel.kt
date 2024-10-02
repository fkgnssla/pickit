package com.ssafy.pickit.ui.main.broadcast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.usecase.vote.EndBroadcastVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingBroadcastVoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BroadCastVoteViewModel @Inject constructor(
    private val ongoingBroadCastVoteListUseCase: OngoingBroadcastVoteListUseCase,
    private val endBroadCastVoteListUseCase: EndBroadcastVoteListUseCase
) : ViewModel() {

    private val _broadcastId = MutableLiveData<String>()
    val broadcastId: LiveData<String> get() = _broadcastId

    private val _isInProgressSelected = MutableLiveData<Boolean>(true)
    val isInProgressSelected: LiveData<Boolean> get() = _isInProgressSelected

    private val _isCompletedSelected = MutableLiveData<Boolean>(false)
    val isCompletedSelected: LiveData<Boolean> get() = _isCompletedSelected

    private val _voteData = MutableLiveData<List<VoteListData>>()
    val voteData: LiveData<List<VoteListData>> get() = _voteData

    private val _stationName = MutableLiveData<String>("test")
    val stationName: LiveData<String> get() = _stationName


    private val stationMapping = mapOf(
        "1" to "SBS",
        "2" to "KBS",
        "3" to "MBC",
        "4" to "JTBC",
        "5" to "Mnet",
        "6" to "tvN",
        "7" to "WATCHA",
        "8" to "Wavve",
        "9" to "coupang play",
        "10" to "Disney",
        "11" to "TVING",
        "12" to "NETFLIX",
        "13" to "TV CHOSUN",
        "14" to "ChannelA",
        "15" to "afreecaTV",
        "16" to "twitch"
    )

    fun setBroadcastId(id: String) {
        _broadcastId.value = id
        _stationName.value = mapStationName(id)

    }

    private fun mapStationName(broadcastId: String): String {
        val stationName = stationMapping[broadcastId] ?: "Unknown Station"
        Log.d("BroadCastVoteViewModel", "Broadcast ID: $broadcastId, Station Name: $stationName")
        return stationName
    }

    fun onInProgressButtonClicked(broadcastId: String) {

        _isInProgressSelected.value = true
        _isCompletedSelected.value = false
        fetchInProgressData(broadcastId)
    }

    fun onCompletedButtonClicked(broadcastId: String) {

        _isInProgressSelected.value = false
        _isCompletedSelected.value = true
        fetchCompletedData(broadcastId)
    }

    private fun fetchInProgressData(broadcastId: String) {
        viewModelScope.launch {
            try {
                val data: List<VoteListData> = ongoingBroadCastVoteListUseCase.invoke(broadcastId)
                _voteData.value = data
            } catch (e: Exception) {
                Log.e("BroadCastVoteViewModel", "Exception: $e")
                _voteData.value = emptyList()
            }
        }
    }

    private fun fetchCompletedData(broadcastId: String) {
        viewModelScope.launch {
            try {
                val data: List<VoteListData> = endBroadCastVoteListUseCase.invoke(broadcastId)
                _voteData.value = data
            } catch (e: Exception) {
                Log.e("BroadCastVoteViewModel", "Exception: $e")
                _voteData.value = emptyList()
            }
        }
    }
}
