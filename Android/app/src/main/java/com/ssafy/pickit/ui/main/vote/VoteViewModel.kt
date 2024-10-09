package com.ssafy.pickit.ui.main.vote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteListDataResponse
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.usecase.vote.EndVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.SearchVoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val ongoingVoteListUseCase: OngoingVoteListUseCase,
    private val endVoteListUseCase: EndVoteListUseCase,
    private val searchVoteListUseCase: SearchVoteListUseCase
) : ViewModel() {

    private val _isInProgressSelected = MutableLiveData<Boolean>(true)
    val isInProgressSelected: LiveData<Boolean> get() = _isInProgressSelected

    private val _isCompletedSelected = MutableLiveData<Boolean>(false)
    val isCompletedSelected: LiveData<Boolean> get() = _isCompletedSelected

    private val _voteData = MutableLiveData<List<VoteListData>>()
    val voteData: LiveData<List<VoteListData>> get() = _voteData

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
        viewModelScope.launch {
            try {
                val data: List<VoteListData> = ongoingVoteListUseCase.invoke()
                _voteData.value = data
            } catch (e: Exception) {
                Log.e("VoteViewModel", "Exception: $e")
                _voteData.value = emptyList()
            }
        }
    }

    fun fetchInSearchData(keyword : String) {
        viewModelScope.launch {
            try {
                val data  = searchVoteListUseCase(keyword)
                _voteData.value =data
            }catch (e: Exception) {
                Log.e("VoteViewModel", "Exception: $e")
                _voteData.value = emptyList()
            }
        }
    }

    private fun fetchCompletedData() {
        viewModelScope.launch {
            try {
                val data: List<VoteListData> = endVoteListUseCase.invoke()
                _voteData.value = data
            } catch (e: Exception) {

                Log.e("VoteViewModel", "Exception: $e")
                _voteData.value = emptyList()
            }
        }
    }
}