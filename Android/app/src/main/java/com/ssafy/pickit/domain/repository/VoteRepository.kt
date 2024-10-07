package com.ssafy.pickit.domain.repository

import com.ssafy.pickit.data.datasource.remote.response.ResponseWrapper
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteSessionResponse
import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.entity.VotePopularData
import com.ssafy.pickit.domain.entity.VoteResultData
import com.ssafy.pickit.domain.entity.VoteSessionData

interface VoteRepository {
    // 진행 중인 투표 목록을 가져오는 함수
    suspend fun getOnGoingVoteList(): List<VoteListData>

    // 종료된 투표 목록을 가져오는 함수
    suspend fun getEndVoteList(): List<VoteListData>

    // 특정 방송의 진행 중인 투표 목록을 가져오는 함수
    suspend fun getOnGoingBroadcastVoteList(broadcastId: String): List<VoteListData>

    // 특정 방송의 종료된 투표 목록을 가져오는 함수
    suspend fun getEndBroadcastVoteList(broadcastId: String): List<VoteListData>

    // 특정 투표에 대해 투표하는 함수
    suspend fun postVote(voteItem: VoteItem): Boolean

   suspend fun getVoteDetail(voteId: String): VoteSessionData

    suspend fun getVoteResult(voteId: String): VoteResultData

    suspend fun getPopularVoteList(): List<VotePopularData>
}