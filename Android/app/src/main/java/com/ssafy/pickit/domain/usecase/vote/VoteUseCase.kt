package com.ssafy.pickit.domain.usecase.vote

import com.ssafy.pickit.data.datasource.remote.response.ResponseWrapper
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteSessionResponse
import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class VoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(voteItem: VoteItem) = voteRepository.postVote(voteItem)



    suspend fun getVoteResult(voteId: String): VoteResultResponse {
        return voteRepository.getVoteResult(voteId)
    }
}