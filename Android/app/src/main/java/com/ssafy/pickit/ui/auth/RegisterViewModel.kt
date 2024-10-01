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
) : ViewModel() {

    val name: MutableLiveData<String> = MutableLiveData("")
    val age: MutableLiveData<String> = MutableLiveData("")
    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    private val _isTermsAccepted = MutableLiveData<Boolean>(false)
    val isTermsAccepted: LiveData<Boolean> get() = _isTermsAccepted

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
        age.observeForever { validateForm() }
        _gender.observeForever { validateForm() }
        isTermsAccepted.observeForever { validateForm() }
    }

    private fun validateForm() {
        _isFormValid.value = isValidInput()
    }

    fun selectGender(selectedGender: String) {
        _gender.value = selectedGender
    }


    fun setTermsAccepted(isAccepted: Boolean) {
        _isTermsAccepted.value = isAccepted
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
                age = age.value?.toIntOrNull() ?: return,
                gender = gender.value ?: return
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

    fun cancel() {

        name.value = ""
        age.value = ""
        _gender.value = ""
        _isTermsAccepted.value = false


        _showToastEvent.value = "작업이 취소되었습니다."
    }

    private fun isValidInput(): Boolean {
        return !name.value.isNullOrEmpty() &&
                !age.value.isNullOrEmpty() &&
                !gender.value.isNullOrEmpty() &&
                (isTermsAccepted.value == true)
    }

    sealed class ApiState {
        object SuccessState : ApiState()
        data class FailState(val error: String) : ApiState()
        object LoadingState : ApiState()
        object DefaultState : ApiState()
    }
}
