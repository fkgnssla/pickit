package com.ssafy.pickit.domain.usecase

import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import com.ssafy.pickit.domain.repository.VoteRepository
import com.ssafy.pickit.domain.model.VoteStatus
import javax.inject.Inject

class VoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(): List<String> {
        return voteRepository.getVoteItems()
    }

    suspend fun getVoteStatus(): VoteStatus {
        return voteRepository.getVoteStatus()
    }

    suspend fun getInProgressData(): List<VoteResponse> {
        return voteRepository.getInProgressData()
    }

    suspend fun getCompletedData(): List<VoteResponse> {
        return voteRepository.getCompletedData()
    }


    suspend fun getVoteDetail(voteId : String): VoteSessionResponse {
        return voteRepository.getVoteDetail(voteId)
    }

    suspend fun getVoteResult(voteId: String): VoteResultResponse {
        return voteRepository.getVoteResult(voteId)
    }

}
