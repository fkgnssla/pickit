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
import com.ssafy.pickit.ui.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
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