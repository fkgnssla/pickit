package com.ssafy.pickit.domain.usecase.vote

import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class EndMyVoteListUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke() = voteRepository.getEndMyVoteList()
}