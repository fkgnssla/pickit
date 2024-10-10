package com.ssafy.pickit.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.pickit.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgreementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButton()
        initView()
    }

    private fun initButton() {
        binding.saveButton.isEnabled = false

        val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, _ ->
            updateButtonState()
        }
        binding.checkBox1.setOnCheckedChangeListener(checkedChangeListener)
        binding.checkBox2.setOnCheckedChangeListener(checkedChangeListener)

        binding.saveButton.setOnClickListener {
            if (binding.checkBox1.isChecked && binding.checkBox2.isChecked) {
                RegisterActivity.start(this)
            }
        }
    }

    private fun initView() {
        binding.termsContainer1.post {
            binding.termsContainer1.minimumHeight = binding.nestedScrollView1.height
        }

        binding.termsContainer2.post {
            binding.termsContainer2.minimumHeight = binding.nestedScrollView2.height
        }

    }

    private fun updateButtonState() {
        binding.saveButton.isEnabled = binding.checkBox1.isChecked && binding.checkBox2.isChecked
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AgreementActivity::class.java)
            context.startActivity(intent)
        }
    }
}