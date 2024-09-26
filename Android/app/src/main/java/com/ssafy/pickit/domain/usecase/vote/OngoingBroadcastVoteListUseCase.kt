package com.ssafy.pickit.domain.usecase.vote

import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class OngoingBroadcastVoteListUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(broadcastId: String) =
        voteRepository.getOnGoingBroadcastVoteList(broadcastId)
}