package com.ssafy.pickit.ui.main.voteDetail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import com.ssafy.pickit.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        setCancelable(false)

        binding.lottieLoading.playAnimation()
        binding.tvLoadingMessage.text = "블록 쌓는중..."
    }

    fun setMessage(message: String) {
        binding.tvLoadingMessage.text = message
    }
}