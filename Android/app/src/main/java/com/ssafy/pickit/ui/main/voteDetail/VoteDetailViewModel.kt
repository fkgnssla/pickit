package com.ssafy.pickit.ui.main.voteDetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.entity.CandidateData
import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.entity.VoteSessionData
import com.ssafy.pickit.domain.usecase.vote.VoteDetailUseCase
import com.ssafy.pickit.domain.usecase.vote.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteDetailViewModel @Inject constructor(
    private val voteDetailUseCase: VoteDetailUseCase,
    private val voteUseCase: VoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var sessionId: String
        get() = savedStateHandle[SESSION_ID_KEY] ?: throw IllegalStateException("Session ID not found")
        private set(value) {
            savedStateHandle[SESSION_ID_KEY] = value
        }

    private val _voteSessionData = MutableLiveData<VoteSessionData>()
    val voteSessionResponse: MutableLiveData<VoteSessionData> get() = _voteSessionData

    private val _userVoted = MutableLiveData<Boolean>(false)
    val userVoted: LiveData<Boolean> get() = _userVoted

    private val _voteState = MutableLiveData<VoteState>(VoteState.DefaultState)
    val voteState: LiveData<VoteState> get() = _voteState



    private var selectedCandidate: CandidateData? = null

    init {
       fetchVoteSessionData(sessionId)
    }



    private fun fetchVoteSessionData(id: String) {
        viewModelScope.launch {
            try {
                val response = voteDetailUseCase(id)
                _voteSessionData.value = response
            } catch (e: Exception) {
                Log.e("VoteDetailViewModel", "Exception: $e")
            }
        }
    }

    fun postVote(candidateId: Long) {
        viewModelScope.launch {
            _voteState.value = VoteState.LoadingState
            val voteSession = voteSessionResponse.value ?: return@launch

            val contractAddress = voteSession.contractAddress
            Log.d("testtttt",contractAddress)
            val voteItem = VoteItem(contractAddress, candidateId)
            val isSuccess = voteUseCase(voteItem)

            if (isSuccess) _voteState.value = VoteState.SuccessState
            else _voteState.value = VoteState.FailureState
        }
    }

    sealed class VoteState {
        object SuccessState : VoteState()
        object FailureState : VoteState()
        object LoadingState : VoteState()
        object DefaultState : VoteState()
    }

    companion object {
        private const val SESSION_ID_KEY = "voteSessionId"
    }

}
