package com.ssafy.pickit.ui.main.voteDetail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.usecase.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ssafy.pickit.data.datasource.remote.response.Candidate
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import java.time.LocalDateTime

@HiltViewModel
class VoteDetailViewModel @Inject constructor(
    private val repository: VoteUseCase
) : ViewModel() {

    private val _voteSessionResponse = MutableLiveData<VoteSessionResponse>()
    val voteSessionResponse: LiveData<VoteSessionResponse> get() = _voteSessionResponse
    private val _userVoted = MutableLiveData<Boolean>(false)
    val userVoted: LiveData<Boolean> get() = _userVoted


    fun setVoteSessionResponse(response: VoteSessionResponse) {
        _voteSessionResponse.value = response
    }

    init {
        // 예시 데이터 설정
        setExampleData()
    }

    private fun setExampleData() {
        val exampleCandidates = listOf(
            Candidate(name = "Person 1", imgUrl = "https://via.placeholder.com/150", voteCnt = 10),
            Candidate(name = "Person 2", imgUrl = "https://via.placeholder.com/150", voteCnt = 20),
            Candidate(name = "Person 3", imgUrl = "https://via.placeholder.com/150", voteCnt = 30),
            Candidate(name = "Person 4", imgUrl = "https://via.placeholder.com/150", voteCnt = 40),
            Candidate(name = "Person 5", imgUrl = "https://via.placeholder.com/150", voteCnt = 50),
            Candidate(name = "Person 6", imgUrl = "https://via.placeholder.com/150", voteCnt = 60),
            Candidate(name = "Person 7", imgUrl = "https://via.placeholder.com/150", voteCnt = 70),
            Candidate(name = "Person 8", imgUrl = "https://via.placeholder.com/150", voteCnt = 80),
            Candidate(name = "Person 9", imgUrl = "https://via.placeholder.com/150", voteCnt = 90)
        )

        val exampleVoteSession = VoteSessionResponse(
            id = "example_id",
            title = "Example Title",
            description = "This is an example description.",
            imgUrl = "https://via.placeholder.com/300",
            candidates = exampleCandidates,
            startDate = LocalDateTime.now().minusDays(1),
            endDate = LocalDateTime.now().plusDays(1)
        )

        _voteSessionResponse.value = exampleVoteSession
    }


    private fun fetchVoteSessionData(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getVoteDetail(id)
                _voteSessionResponse.value = response
            } catch (e: Exception) {
            }
        }
    }
}
