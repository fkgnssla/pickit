package com.ssafy.pickit.domain.usecase.vote

import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class VoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(voteItem: VoteItem) = voteRepository.postVote(voteItem)
}