package com.ssafy.pickit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.data.datasource.remote.response.Candidate
import com.ssafy.pickit.data.datasource.remote.response.CandidateResult
import com.ssafy.pickit.data.datasource.remote.response.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import com.ssafy.pickit.domain.usecase.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val voteUseCase: VoteUseCase
) : ViewModel() {



    private val _selectedCandidateId = MutableLiveData<String>()
    val selectedCandidateId: LiveData<String> get() = _selectedCandidateId

    private val _voteSessionResponse = MutableLiveData<VoteSessionResponse?>()
    val voteSessionResponse: MutableLiveData<VoteSessionResponse?> get() = _voteSessionResponse

    private val _voteResultResponse = MutableLiveData<VoteResultResponse>()
    val voteResultResponse: LiveData<VoteResultResponse> get() = _voteResultResponse

    fun setSelectedCandidateId(candidateId: String) {
        _selectedCandidateId.value = candidateId
    }

    fun fetchVoteSessionData(id: String) {
        viewModelScope.launch {
            try {

                val response = voteUseCase.getVoteDetail(id)


                if (response.status == "SUCCESS") {


                    val data = response.data ?: run {
                        Log.e("VoteViewModel", "No data received")
                        _voteSessionResponse.value = null
                        return@launch
                    }


                    _voteSessionResponse.value = data

                } else {

                    Log.e("VoteViewModel", "Error fetching data: ${response.message}")
                    _voteSessionResponse.value = null
                }

            } catch (e: Exception) {
                // 예외 발생 시 에러 처리
                Log.e("VoteViewModel", "Exception: $e")
                _voteSessionResponse.value = null
            }
        }
    }



    fun fetchVoteResultData(voteId: String) {
        viewModelScope.launch {
            try {
                val response = voteUseCase.getVoteResult(voteId) // VoteResultResponse를 반환
                _voteResultResponse.value = response
            } catch (e: Exception) {
                // 에러 처리
                setExampleVoteResultData()
            }
        }
    }

    private fun setExampleVoteResultData() {
        val exampleResults = (1..3).map { index ->
            CandidateResult(
                candidateId = index.toString(),
                candidateName = "후보자 $index",
                voteCount = (Math.random() * 300).toLong()
            )
        }


        setSelectedCandidateId("1")

        val exampleVoteSession = VoteSessionResponse(
            id = "1",
            title = "예시 투표",
            description = "투표 설명",
            thumbnail = "https://via.placeholder.com/150",
            candidates = exampleResults.map { Candidate(it.candidateName, "", it.voteCount) }, // Candidate로 변환
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusDays(1)
        )

        val voteResultResponse = VoteResultResponse(exampleVoteSession, exampleResults)
        _voteResultResponse.value = voteResultResponse
    }
}
