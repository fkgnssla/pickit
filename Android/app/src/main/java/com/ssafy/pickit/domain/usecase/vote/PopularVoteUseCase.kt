package com.ssafy.pickit.domain.usecase.vote

import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.entity.VotePopularData
import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class PopularVoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(): List<VotePopularData> = voteRepository.getPopularVoteList()
}