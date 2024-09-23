package com.ssafy.pickit.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.viewModelScope
import com.ssafy.pickit.domain.usecase.wallet.GenerateWalletUseCase
import com.ssafy.pickit.domain.usecase.wallet.InsertWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalleltViewModel @Inject constructor(
    private val generateWalletUseCase: GenerateWalletUseCase,
    private val insertWalletUseCase: InsertWalletUseCase
) : ViewModel(){

    fun generateWallet() {
        viewModelScope.launch {
            generateWalletUseCase()
        }
    }

    fun insertWallet(privateKey : String) {
        viewModelScope.launch {
            insertWalletUseCase(privateKey)
        }
    }
}