package com.ssafy.pickit.domain.usecase.vote

import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class SearchVoteListUseCase @Inject constructor(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(keyword: String) =
        voteRepository.getSearchVoteList(keyword)
}