package com.ssafy.pickit.ui.main.mypage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.ssafy.pickit.databinding.ActivityUserProfileBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileActivity : AppCompatActivity() {

    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchUserData()

        viewModel.userData.observe(this) { userData ->
            userData?.let {
                binding.nameTextView.text = it.name
                binding.ageTextView.text = it.age.toString()
                binding.genderTextView.text = it.gender
            }
        }
    }
}
