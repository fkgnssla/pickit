package com.ssafy.pickit.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import com.ssafy.pickit.R
import com.ssafy.pickit.common.BaseFragment
import com.ssafy.pickit.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = this@MyPageFragment
        }

        // member_info 버튼 클릭 시 다이얼로그를 표시하는 리스너 설정
        binding.memberInfo.setOnClickListener {
            showMemberInfoDialog()
        }

        // member_modify 버튼 클릭 시 수정 화면을 다이얼로그로 띄우는 리스너 설정
        binding.memberModify.setOnClickListener {
            showMemberModifyDialog()
        }

    }

    private fun showMemberInfoDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("회원 정보")

        builder.setMessage("이름: 김싸피\n성별: 남\n나이: 25세\n연동ID: ssafy@ssafy.com\n닉네임: 싸피123\n아이디: sssafy")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showMemberModifyDialog() {
        // fragment_user_modify.xml 레이아웃을 인플레이트
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.fragment_user_modify, null)

        // AlertDialog 빌더 사용
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("회원 정보 수정")
            .setView(dialogView)
            .setPositiveButton("저장") { dialog, _ ->
                // 수정 내용 처리
                val presentPassword = dialogView.findViewById<EditText>(R.id.presentPassword).text.toString()
                val editNickName = dialogView.findViewById<EditText>(R.id.editNickName).text.toString()
                val newPassword = dialogView.findViewById<EditText>(R.id.newPassword).text.toString()
                val newPasswordConfirm = dialogView.findViewById<EditText>(R.id.newPasswordConfirm).text.toString()

                // 여기서 입력한 내용을 처리하거나 viewModel을 통해 데이터 업데이트
                handleUserModification(presentPassword, editNickName, newPassword, newPasswordConfirm)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }

        // 다이얼로그를 생성하고 표시
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun handleUserModification(presentPassword: String, editNickName: String, newPassword: String, newPasswordConfirm: String) {
        // 여기서 입력한 데이터로 원하는 작업을 처리할 수 있습니다.
        // 예를 들어, ViewModel을 통해 데이터를 서버에 전송하거나 로컬 데이터베이스에 저장할 수 있습니다.
        println("현재 비밀번호: $presentPassword")
        println("새 닉네임: $editNickName")
        println("새 비밀번호: $newPassword")
        println("비밀번호 확인: $newPasswordConfirm")
    }
}
