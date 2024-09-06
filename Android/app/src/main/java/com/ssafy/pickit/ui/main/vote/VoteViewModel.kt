package com.ssafy.pickit.ui.main.vote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.usecase.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val voteUseCase: VoteUseCase
) : ViewModel() {

    private val _voteItems = MutableLiveData<List<String>?>()
    val voteItems: MutableLiveData<List<String>?> get() = _voteItems

    init {
        fetchVoteItems()
    }

    private fun fetchVoteItems() {
        viewModelScope.launch {
            _voteItems.value = voteUseCase()
        }
    }
}