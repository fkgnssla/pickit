package com.ssafy.pickit.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.common.validateBirthDate
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
) : ViewModel() {

    val name: MutableLiveData<String> = MutableLiveData("")
    val birthday: MutableLiveData<String> = MutableLiveData("")
    val gender: MutableLiveData<String> = MutableLiveData("")

    private val _showToastEvent = MutableLiveData<String>()
    val showToastEvent: LiveData<String> = _showToastEvent

    private val _registerState = MutableLiveData<ApiState>(ApiState.DefaultState)
    val registerState: LiveData<ApiState> = _registerState

    private val _walletState = MutableLiveData<ApiState>(ApiState.DefaultState)
    val walletState: LiveData<ApiState> = _walletState

    private val _isFormValid = MutableLiveData<Boolean>(false)
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    init {
        name.observeForever { validateForm() }
        birthday.observeForever { validateForm() }
        gender.observeForever { validateForm() }
    }

    private fun validateForm() {
        _isFormValid.value = isValidInput()
    }

    fun createWallet() {
        viewModelScope.launch {
            _walletState.value = ApiState.LoadingState
            try {
                generateWalletUseCase()
                _walletState.value = ApiState.SuccessState
            } catch (e: Exception) {
                _walletState.value = ApiState.FailState(e.message ?: "Unknown Error")
            }
        }
    }

    fun register() {
        if (isValidInput()) {
            val registerItem = RegisterItem(
                name = name.value ?: return,
                birthday = birthday.value ?: return,
                gender = gender.value?.let { if (it.toInt() % 2 == 1) "male" else "female" }
                    ?: return
            )

            viewModelScope.launch {
                _registerState.value = ApiState.LoadingState
                try {
                    val isSuccess = registerUseCase(registerItem)
                    _registerState.value = if (isSuccess) {
                        ApiState.SuccessState
                    } else {
                        ApiState.FailState("Registration Failed")
                    }
                } catch (e: Exception) {
                    _registerState.value = ApiState.FailState(e.message ?: "Unknown Error")
                }
            }
        } else {
            _showToastEvent.value = "모든 필드를 올바르게 입력해 주세요."
        }
    }

    private fun isValidInput(): Boolean {
        return !name.value.isNullOrEmpty() &&
                !birthday.value.isNullOrEmpty() &&
                !gender.value.isNullOrEmpty() &&
                validateBirthDate(birthday.value ?: return false) &&
                isValidGender(gender.value ?: return false)
    }

    private fun isValidGender(gender: String): Boolean = gender.toInt() in 1..4

    sealed class ApiState {
        object SuccessState : ApiState()
        data class FailState(val error: String) : ApiState()
        object LoadingState : ApiState()
        object DefaultState : ApiState()
    }
}
