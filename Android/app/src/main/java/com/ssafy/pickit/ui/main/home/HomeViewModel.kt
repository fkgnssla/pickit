package com.ssafy.pickit.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.pickit.R
import com.ssafy.pickit.ui.main.channel.ChannelButton

class HomeViewModel : ViewModel() {

    private val _buttonClicked = MutableLiveData<Int>()
    val buttonClicked: LiveData<Int> get() = _buttonClicked

    private val _items = MutableLiveData<List<Int>>()
    val items: LiveData<List<Int>> get() = _items

    private val _channelButtons = MutableLiveData<List<ChannelButton>>()
    val channelButtons: LiveData<List<ChannelButton>> get() = _channelButtons

    init {
        _items.value = listOf(
            R.drawable.ic_vote1,
            R.drawable.ic_vote2,
            R.drawable.ic_vote3,
            R.drawable.ic_vote4,
            R.drawable.ic_vote5
        )

        _channelButtons.value = (1..16).map { ChannelButton(it, getIconResId(it)) }
    }

    fun onButtonClick(buttonNumber: Int) {
        _buttonClicked.value = buttonNumber
    }

    private fun getIconResId(id: Int): Int {
        return when (id) {
            1 -> R.drawable.ic_channel_1
            2 -> R.drawable.ic_channel_2
            3 -> R.drawable.ic_channel_3
            4 -> R.drawable.ic_channel_4
            5 -> R.drawable.ic_channel_5
            6 -> R.drawable.ic_channel_6
            7 -> R.drawable.ic_channel_7
            8 -> R.drawable.ic_channel_8
            9 -> R.drawable.ic_channel_9
            10 -> R.drawable.ic_channel_10
            11 -> R.drawable.ic_channel_11
            12 -> R.drawable.ic_channel_12
            13 -> R.drawable.ic_channel_13
            14 -> R.drawable.ic_channel_14
            15 -> R.drawable.ic_channel_15
            16 -> R.drawable.ic_channel_16
            else -> R.drawable.ic_channel_1
        }
    }
}