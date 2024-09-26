package com.ssafy.pickit.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.usecase.auth.LoginUseCase
import com.ssafy.pickit.domain.usecase.vote.EndBroadcastVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.EndVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingBroadcastVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingVoteListUseCase
import com.ssafy.pickit.ui.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val ongoingBroadcastVoteListUseCase: OngoingBroadcastVoteListUseCase,
    private val ongoingVoteListUseCase: OngoingVoteListUseCase,
    private val endVoteListUseCase: EndVoteListUseCase,
    private val endBroadcastVoteListUseCase: EndBroadcastVoteListUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.LoadingState)
    val loginState: LiveData<LoginState> = _loginState

    fun login(token: String) {
        viewModelScope.launch {
            val isOld: Boolean = loginUseCase(LogInItem(token)).isExist
            if (isOld) _loginState.value = LoginState.OldUserState
            else _loginState.value = LoginState.NewUserState
        }

    }

    fun test() {
        viewModelScope.launch {
            val data1 = ongoingVoteListUseCase()
            val data2 = ongoingBroadcastVoteListUseCase("66f132e160e1086af849100d")
            val data3 = endVoteListUseCase()
            val data4 = endBroadcastVoteListUseCase("66f132e160e1086af849100d")
        }
    }

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("kakaoLogin", "카카오계정 로그인 성공 ${token.accessToken}")
            login(token.accessToken)
        } else if (error != null) {
            Log.d("kakaoLogin", error.toString())
        }
    }

    sealed class LoginState {
        object OldUserState : LoginState()
        object NewUserState : LoginState()
        object LoadingState : LoginState()
    }

}