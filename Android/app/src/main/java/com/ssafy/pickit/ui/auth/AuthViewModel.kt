package com.ssafy.pickit.ui.auth

import androidx.lifecycle.ViewModel
import com.ssafy.pickit.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: AuthUseCase
) : ViewModel() {

}