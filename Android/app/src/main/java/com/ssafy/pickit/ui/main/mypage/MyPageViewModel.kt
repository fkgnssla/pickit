package com.ssafy.pickit.ui.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.entity.UserData
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.usecase.auth.UserUseCase
import com.ssafy.pickit.domain.usecase.vote.EndMyVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingMyVoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val ongoingMyVoteListUseCase: OngoingMyVoteListUseCase,
    private val endMyVoteListUseCase: EndMyVoteListUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    private val _ongoingMyVoteList = MutableLiveData<List<VoteListData>>()
    val ongoingMyVoteList: LiveData<List<VoteListData>> get() = _ongoingMyVoteList

    private val _endMyVoteList = MutableLiveData<List<VoteListData>>()
    val endMyVoteList: LiveData<List<VoteListData>> get() = _endMyVoteList

    init {
        getUserData()
        getOngoingMyVoteList()
        getEndMyVoteList()
    }

    private fun getOngoingMyVoteList() {
        viewModelScope.launch {
            val data = ongoingMyVoteListUseCase()
            _ongoingMyVoteList.value = data
        }
    }

    private fun getEndMyVoteList() {
        viewModelScope.launch {
            val data = endMyVoteListUseCase()
            _endMyVoteList.value = data
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            val data = userUseCase()
            _userData.value = data
        }
    }

}