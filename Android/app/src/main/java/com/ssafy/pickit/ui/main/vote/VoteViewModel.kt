package com.ssafy.pickit.ui.main.vote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.R
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

    private fun fetchInProgressData() {
        viewModelScope.launch {
            // 실제 REST API 호출 예시
//            try {
//                val data = voteUseCase.getInProgressData()
//                _voteData.value = data.map { vote ->
//                    VoteResponse(
//                        imageUrl = vote.imageUrl,
//                        title = vote.title,
//                        startDate = vote.startDate,
//                        endDate = vote.endDate
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("VoteViewModel", "Error fetching in-progress data", e)
//            }


            val exampleData = listOf(
                VoteResponse(
                    id="1",
                    imgUrl = "https://via.placeholder.com/300",
                    title = "진행 중 항목 1",
                    startDate = LocalDateTime.of(2024, 9, 1, 0, 0),
                    endDate = LocalDateTime.of(2024, 9, 10, 0, 0)
                ),
                VoteResponse(
                    id="2",
                    imgUrl = "https://via.placeholder.com/300",
                    title = "진행 중 항목 2",
                    startDate = LocalDateTime.of(2024, 9, 2, 0, 0),
                    endDate = LocalDateTime.of(2024, 9, 12, 0, 0)
                ),
                VoteResponse(
                    id="3",
                    imgUrl = "https://via.placeholder.com/300",
                    title = "진행 중 항목 3",
                    startDate = LocalDateTime.of(2024, 9, 3, 0, 0),
                    endDate = LocalDateTime.of(2024, 9, 13, 0, 0)
                )
            )
            _voteData.value = exampleData
        }
    }

    private fun fetchCompletedData() {
        viewModelScope.launch {

//            try {
//                val data = voteUseCase.getCompletedData()
//                _voteData.value = data.map { vote ->
//                    VoteResponse(
//                        imageUrl = vote.imageUrl,
//                        title = vote.title,
//                        startDate = vote.startDate,
//                        endDate = vote.endDate
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("VoteViewModel", "Error fetching completed data", e)
//            }


            val exampleData = listOf(
                VoteResponse(
                    id="1",
                    imgUrl = "https://via.placeholder.com/300",
                    title = "진행 중 항목 1",
                    startDate = LocalDateTime.of(2024, 8, 1, 0, 0),
                    endDate = LocalDateTime.of(2024, 8, 10, 0, 0)
                ),
                VoteResponse(
                    id="2",
                    imgUrl = "https://via.placeholder.com/300",
                    title = "진행 중 항목 2",
                    startDate = LocalDateTime.of(2024, 8, 2, 0, 0),
                    endDate = LocalDateTime.of(2024, 8, 12, 0, 0)
                ),
                VoteResponse(
                    id="3",
                    imgUrl = "https://via.placeholder.com/300",
                    title = "진행 중 항목 3",
                    startDate = LocalDateTime.of(2024, 8, 3, 0, 0),
                    endDate = LocalDateTime.of(2024, 8, 13, 0, 0)
                )
            )
            _voteData.value = exampleData
        }
    }
}
