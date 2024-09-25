package com.ssafy.pickit.ui.main.vote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.domain.usecase.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val voteUseCase: VoteUseCase
) : ViewModel() {

    private val _isInProgressSelected = MutableLiveData<Boolean>(true)
    val isInProgressSelected: LiveData<Boolean> get() = _isInProgressSelected

    private val _isCompletedSelected = MutableLiveData<Boolean>(false)
    val isCompletedSelected: LiveData<Boolean> get() = _isCompletedSelected

    private val _voteData = MutableLiveData<List<VoteResponse>>()
    val voteData: LiveData<List<VoteResponse>> get() = _voteData

    init {
        fetchInProgressData()
    }

    fun onInProgressButtonClicked() {
        Log.d("VoteViewModel", "InProgress button clicked")
        _isInProgressSelected.value = true
        _isCompletedSelected.value = false
        fetchInProgressData()
    }

    fun onCompletedButtonClicked() {
        Log.d("VoteViewModel", "Completed button clicked")
        _isInProgressSelected.value = false
        _isCompletedSelected.value = true
        fetchCompletedData()
    }

    fun fetchInProgressData() {
        setExampleInProgressData()
//        viewModelScope.launch {
//            try {
//                val response = voteUseCase.getInProgressVotes()
//
//                if (response.status == "SUCCESS") {
//
//                    val data = response.data ?: run {
//                        Log.e("VoteViewModel", "No data received")
//                        _voteData.value = emptyList()
//                        return@launch // 함수 종료
//                    }
//                    _voteData.value = data
//                } else {
//
//                    Log.e("VoteViewModel", "Error fetching data: ${response.message}")
//                    _voteData.value = emptyList()
//                }
//            } catch (e: Exception) {
//                Log.e("VoteViewModel", "Exception: $e")
//                _voteData.value = emptyList()
//            }
//        }
    }

    private fun fetchCompletedData() {
        setExampleCompletedData()
//        viewModelScope.launch {
//            try {
//                val response = voteUseCase.getCompletedVotes()
//                if (response.status == "SUCCESS") {
//
//                    val data = response.data ?: run {
//                        Log.e("VoteViewModel", "No data received")
//                        _voteData.value = emptyList()
//                        return@launch
//                    }
//                    _voteData.value = data
//                } else {
//
//                    Log.e("VoteViewModel", "Error fetching data: ${response.message}")
//                    _voteData.value = emptyList()
//                }
//            } catch (e: Exception) {
//                Log.e("VoteViewModel", "Exception: $e")
//                _voteData.value = emptyList()
//            }
//        }
    }


    private fun setExampleInProgressData() {
        val exampleData = listOf(
            VoteResponse(
                id = "1",
                thumbnail = "https://via.placeholder.com/300",
                title = "진행 중 항목 1",
                startDate = LocalDateTime.of(2024, 9, 1, 0, 0),
                endDate = LocalDateTime.of(2024, 9, 10, 0, 0)
            ),
            VoteResponse(
                id = "2",
                thumbnail = "https://via.placeholder.com/300",
                title = "진행 중 항목 2",
                startDate = LocalDateTime.of(2024, 9, 2, 0, 0),
                endDate = LocalDateTime.of(2024, 9, 12, 0, 0)
            ),
            VoteResponse(
                id = "3",
                thumbnail = "https://via.placeholder.com/300",
                title = "진행 중 항목 3",
                startDate = LocalDateTime.of(2024, 9, 3, 0, 0),
                endDate = LocalDateTime.of(2024, 9, 13, 0, 0)
            )
        )
        _voteData.value = exampleData
    }

    private fun setExampleCompletedData() {
        val exampleData = listOf(
            VoteResponse(
                id = "1",
                thumbnail = "https://via.placeholder.com/300",
                title = "종료된 항목 1",
                startDate = LocalDateTime.of(2024, 8, 1, 0, 0),
                endDate = LocalDateTime.of(2024, 8, 10, 0, 0)
            ),
            VoteResponse(
                id = "2",
                thumbnail = "https://via.placeholder.com/300",
                title = "종료된 항목 2",
                startDate = LocalDateTime.of(2024, 8, 2, 0, 0),
                endDate = LocalDateTime.of(2024, 8, 12, 0, 0)
            ),
            VoteResponse(
                id = "3",
                thumbnail = "https://via.placeholder.com/300",
                title = "종료된 항목 3",
                startDate = LocalDateTime.of(2024, 8, 3, 0, 0),
                endDate = LocalDateTime.of(2024, 8, 13, 0, 0)
            )
        )
        _voteData.value = exampleData
    }
}
