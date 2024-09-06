package com.ssafy.pickit.domain.usecase

import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class VoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(): List<String> {
        return voteRepository.getVoteItems()
    }
}