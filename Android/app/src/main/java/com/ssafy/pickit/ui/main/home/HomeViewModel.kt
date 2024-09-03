package com.ssafy.pickit.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.pickit.R

class HomeViewModel : ViewModel() {

    private val _buttonClicked = MutableLiveData<Int>()
    val buttonClicked: LiveData<Int> get() = _buttonClicked

    private val _items = MutableLiveData<List<Int>>()
    val items: LiveData<List<Int>> get() = _items

    init {
        _items.value = listOf(
            R.drawable.ic_vote1,
            R.drawable.ic_vote2,
            R.drawable.ic_vote3,
            R.drawable.ic_vote4,
            R.drawable.ic_vote5
        )
    }

    fun onButtonClick(buttonNumber: Int) {
        _buttonClicked.value = buttonNumber
    }
}