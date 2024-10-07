package com.ssafy.pickit.ui.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.entity.UserData

import com.ssafy.pickit.domain.usecase.auth.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    fun fetchUserData() {
        viewModelScope.launch {
            try {
                val response = userUseCase.invoke()
                _userData.value = response
            } catch (e: Exception) {
                
            }
        }
    }
}
