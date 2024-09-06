package com.ssafy.pickit.data.repositoryImpl

import com.ssafy.pickit.data.datasource.remote.api.vote.VoteApi
import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val voteApi: VoteApi
) : VoteRepository {
    override suspend fun getVoteItems(): List<String> {
        return voteApi.getVoteItems()
    }
}
