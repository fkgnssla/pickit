package com.ssafy.pickit.ui.wallet

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.ssafy.pickit.R
import com.ssafy.pickit.ui.SplashActivity
import com.ssafy.pickit.ui.auth.AuthViewModel
import com.ssafy.pickit.ui.main.MainActivity

class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedScrollView = view.findViewById<NestedScrollView>(R.id.nestedScrollView)
        val linearAreaWrapper = view.findViewById<LinearLayout>(R.id.termsContainer)


        linearAreaWrapper.post {
            linearAreaWrapper.minimumHeight = nestedScrollView.height
        }
        val nameInput: EditText = view.findViewById(R.id.nameInput)
        val ageInput: EditText = view.findViewById(R.id.ageInput)
        val genderGroup: RadioGroup = view.findViewById(R.id.genderGroup)
        val walletNum: TextView = view.findViewById(R.id.walletNum)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        walletNum.text = generateWalletNumber()

        val saveButton: Button = view.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()
            val selectedGenderId = genderGroup.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                view.findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                null
            }

            if (validateForm(name, age, gender, checkBox.isChecked)) {

                handleFormSubmission(name, age, gender ?: "Not specified")
            } else {
                showAlertDialog()
            }
        }
        val cancelButton: Button = view.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            cancelFormSubmission()
        }
    }

    private fun generateWalletNumber(): String {
        return "asdfghj12345(자동생성)"
    }

    private fun validateForm(name: String, age: String, gender: String?, isChecked: Boolean): Boolean {
        return name.isNotEmpty() && age.isNotEmpty() && gender != null && isChecked
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("입력 오류")
            .setMessage("모든 필드를 입력하고 체크박스를 선택해주세요.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun handleFormSubmission(name: String, age: String, gender: String) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun cancelFormSubmission() {
        val intent = Intent(requireContext(), AuthViewModel::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
