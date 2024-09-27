package com.ssafy.pickit.ui.wallet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletActivity : AppCompatActivity() {
    private val binding: ActivityWalletBinding by lazy {
        ActivityWalletBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: WalleltViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WalleltViewModel::class.java]
        binding.apply {
            lifecycleOwner = this@WalletActivity
            this.walletViewModel = viewModel
        }

        binding.btnCreateWallet.setOnClickListener {
            viewModel.generateWallet()
        }
    }
}