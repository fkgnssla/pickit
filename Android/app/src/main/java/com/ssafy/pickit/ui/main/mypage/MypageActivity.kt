package com.ssafy.pickit.ui.main.mypage

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.pickit.R
import com.ssafy.pickit.databinding.ActivityMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : AppCompatActivity() {

    private lateinit var myPageTitle: TextView
    private lateinit var profileImage: ImageView
    private lateinit var nickName: TextView
    private lateinit var voteNum: TextView
    private lateinit var memberInfoButton: Button
    private lateinit var memberModifyButton: Button
    private lateinit var myVotesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // findViewById로 뷰를 가져옴
        myPageTitle = findViewById(R.id.my_page)
        profileImage = findViewById(R.id.profile_image)
        nickName = findViewById(R.id.nick_name)
        voteNum = findViewById(R.id.vote_num)
        memberInfoButton = findViewById(R.id.member_info)
        memberModifyButton = findViewById(R.id.member_modify)
        myVotesButton = findViewById(R.id.my_votes)

        // 사용자 정보를 설정
        displayUserInfo("김싸피", 3)

        // 버튼 클릭 리스너 설정
        memberInfoButton.setOnClickListener {
            // 회원 정보 버튼 클릭 시 동작
            showMemberInfo()
        }

        memberModifyButton.setOnClickListener {
            // 회원 정보 변경 버튼 클릭 시 동작
            modifyMemberInfo()
        }

        myVotesButton.setOnClickListener {
            // 나의 투표 목록 버튼 클릭 시 동작
            showMyVotes()
        }
    }

    private fun displayUserInfo(name: String, voteCount: Int) {
        nickName.text = name
        voteNum.text = "총 투표 수: $voteCount"
        // 프로필 이미지 세팅 (예시로 drawable 리소스 사용)
        profileImage.setImageResource(R.drawable.profile_image)
    }

    private fun showMemberInfo() {
        // 회원 정보 보여주는 로직 (예: 새 액티비티 시작 또는 다이얼로그)
        Toast.makeText(this, "회원 정보", Toast.LENGTH_SHORT).show()
    }

    private fun modifyMemberInfo() {
        // 회원 정보 변경하는 로직 (예: 새 액티비티 시작)
        Toast.makeText(this, "회원 정보 변경", Toast.LENGTH_SHORT).show()
    }

    private fun showMyVotes() {
        // 나의 투표 목록 보여주는 로직 (예: 새 액티비티 시작)
        Toast.makeText(this, "나의 투표 목록", Toast.LENGTH_SHORT).show()
    }
}
