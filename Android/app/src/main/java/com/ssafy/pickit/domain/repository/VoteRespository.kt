package com.ssafy.pickit.domain.repository

import com.ssafy.pickit.data.datasource.remote.response.ApiResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import com.ssafy.pickit.domain.model.VoteStatus

interface VoteRepository {
    suspend fun getVoteItems(): List<String>
    suspend fun getVoteStatus(): VoteStatus
    suspend fun getInProgressVotes(): ApiResponse<List<VoteResponse>>
    suspend fun getCompletedVotes(): ApiResponse<List<VoteResponse>>
    suspend fun getVoteDetail(voteId:String): ApiResponse<VoteSessionResponse>
    suspend fun getVoteResult(voteId: String): VoteResultResponse
}