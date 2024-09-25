package com.ssafy.pickit.data.repositoryImpl

import com.ssafy.pickit.data.datasource.remote.api.vote.VoteApi
import com.ssafy.pickit.data.datasource.remote.response.ApiResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import com.ssafy.pickit.domain.repository.VoteRepository
import com.ssafy.pickit.domain.model.VoteStatus
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val voteApi: VoteApi
) : VoteRepository {
    override suspend fun getVoteItems(): List<String> {
        return voteApi.getVoteItems()
    }

    override suspend fun getVoteStatus(): VoteStatus {
        return voteApi.getVoteStatus()
    }

    override suspend fun getInProgressVotes(): ApiResponse<List<VoteResponse>> {

        return voteApi.getInProgressVotes()
    }

    override suspend fun getCompletedVotes(): ApiResponse<List<VoteResponse>> {
        return voteApi.getCompletedVotes()
    }

    override suspend fun getVoteDetail(voteId: String): ApiResponse<VoteSessionResponse>{
        return voteApi.getVoteDetail(voteId)
    }

    override suspend fun getVoteResult(voteId: String): VoteResultResponse {
        return voteApi.getVoteResult(voteId)
    }
}