package com.ssafy.pickit.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.entity.RegisterItem
import com.ssafy.pickit.domain.usecase.auth.RegisterUseCase
import com.ssafy.pickit.domain.usecase.wallet.GenerateWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val generateWalletUseCase: GenerateWalletUseCase
) : ViewModel(
) {
    private val _registerState = MutableLiveData<ApiState>(ApiState.DefaultState)
    val registerState: LiveData<ApiState> = _registerState

    private val _walletState = MutableLiveData<ApiState>(ApiState.DefaultState)
    val walletState: LiveData<ApiState> = _walletState

    val name: MutableLiveData<String> = MutableLiveData<String>("")
    val age: MutableLiveData<String> = MutableLiveData<String>("")


    fun register() {
        val registerName = name.value ?: return
        val ageString = age.value?.takeIf { it.isNotEmpty() } ?: return
        val registerAge = ageString.toIntOrNull() ?: return
        viewModelScope.launch {
            _registerState.value = ApiState.LoadingState
            val isSuccess = registerUseCase(registerItem = RegisterItem(registerName, registerAge))
            if (isSuccess) _registerState.value = ApiState.SuccessState
            else _registerState.value = ApiState.FailState
        }
    }

    fun generateWallet() {
        viewModelScope.launch {
            _walletState.value = ApiState.LoadingState
            try {
                generateWalletUseCase()
                _walletState.value = ApiState.SuccessState
            } catch (e: Exception) {
                _walletState.value = ApiState.FailState
            }
        }
    }

    sealed class ApiState {
        object SuccessState : ApiState()
        object FailState : ApiState()
        object LoadingState : ApiState()
        object DefaultState : ApiState()
    }
}