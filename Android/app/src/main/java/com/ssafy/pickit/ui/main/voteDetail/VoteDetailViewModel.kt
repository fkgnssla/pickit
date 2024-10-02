package com.ssafy.pickit.ui.main.voteDetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.ssafy.pickit.data.datasource.remote.response.vote.VoteSessionResponse
import com.ssafy.pickit.domain.entity.VoteSessionData
import com.ssafy.pickit.domain.usecase.vote.VoteDetailUseCase
import com.ssafy.pickit.domain.usecase.vote.VoteUseCase
import java.time.LocalDateTime

@HiltViewModel
class VoteDetailViewModel @Inject constructor(
    private val repository: VoteDetailUseCase,

    ) : ViewModel() {

    private val _voteSessionData = MutableLiveData<VoteSessionData>()
    val voteSessionResponse: MutableLiveData<VoteSessionData> get() = _voteSessionData
    private val _userVoted = MutableLiveData<Boolean>(false)
    val userVoted: LiveData<Boolean> get() = _userVoted

    init {
//        fetchVoteSessionData("1")
    }


//    private fun setExampleData(id: String) {
//        val exampleCandidates = listOf(
//            Candidate(name = "Person 1", profileImg = "https://via.placeholder.com/150", voteCnt = 10),
//            Candidate(name = "Person 2", profileImg = "https://via.placeholder.com/150", voteCnt = 20),
//            Candidate(name = "Person 3", profileImg = "https://via.placeholder.com/150", voteCnt = 30),
//            Candidate(name = "Person 4", profileImg = "https://via.placeholder.com/150", voteCnt = 40),
//            Candidate(name = "Person 5", profileImg = "https://via.placeholder.com/150", voteCnt = 50),
//            Candidate(name = "Person 6", profileImg = "https://via.placeholder.com/150", voteCnt = 60),
//            Candidate(name = "Person 7", profileImg = "https://via.placeholder.com/150", voteCnt = 70),
//            Candidate(name = "Person 8", profileImg = "https://via.placeholder.com/150", voteCnt = 80),
//            Candidate(name = "Person 9", profileImg = "https://via.placeholder.com/150", voteCnt = 90)
//        )
//
//        val exampleVoteSession = when (id) {
//            "1" -> VoteSessionResponse(
//                id = id,
//                title = "Title for Vote 1",
//                description = "Description for Vote 1.",
//                thumbnail = "https://via.placeholder.com/300",
//                candidates = exampleCandidates.take(3), // 3명의 후보
//                startDate = LocalDateTime.now().minusDays(1),
//                endDate = LocalDateTime.now().plusDays(1)
//            )
//            "2" -> VoteSessionResponse(
//                id = id,
//                title = "Title for Vote 2",
//                description = "Description for Vote 2.",
//                thumbnail = "https://via.placeholder.com/300",
//                candidates = exampleCandidates.take(6), // 6명의 후보
//                startDate = LocalDateTime.now().minusDays(2),
//                endDate = LocalDateTime.now().plusDays(2)
//            )
//            "3" -> VoteSessionResponse(
//                id = id,
//                title = "Title for Vote 3",
//                description = "Description for Vote 3.",
//                thumbnail = "https://via.placeholder.com/300",
//                candidates = exampleCandidates.take(9), // 9명의 후보
//                startDate = LocalDateTime.now().minusDays(3),
//                endDate = LocalDateTime.now().plusDays(3)
//            )
//            else -> null
//        }
//
//        _voteSessionResponse.value = exampleVoteSession ?: VoteSessionResponse(
//            id = "default_id",
//            title = "Default Title",
//            description = "No valid vote session found.",
//            thumbnail = "https://via.placeholder.com/300",
//            candidates = emptyList(),
//            startDate = LocalDateTime.now(),
//            endDate = LocalDateTime.now().plusDays(1)
//        )
//
//    }

    fun fetchVoteSessionData(id: String) {
        viewModelScope.launch {
            try {

//                setExampleData(id)

                 val response = repository.invoke(id)
                 _voteSessionData.value = response
            } catch (e: Exception) {
                Log.e("VoteDetailViewModel", "Exception: $e")


            }
        }
    }




}
