package com.ssafy.pickit.data.repositoryImpl

import com.ssafy.pickit.data.datasource.remote.api.vote.VoteApi
import com.ssafy.pickit.data.datasource.remote.request.vote.VoteRequest
import com.ssafy.pickit.data.mapper.VoteMapper
import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

//TODO : 실패시 예외 response 처리할 것
class VoteRepositoryImpl @Inject constructor(
    private val voteApi: VoteApi
) : VoteRepository {
    override suspend fun getOnGoingVoteList(): List<VoteListData> {
        val response = voteApi.getOnGoingVoteList()
        val data = response.data!!
        return VoteMapper.mapperToVoteListData(data)
    }

    override suspend fun getEndVoteList(): List<VoteListData> {
        val response = voteApi.getEndVoteList()
        val data = response.data!!
        return VoteMapper.mapperToVoteListData(data)
    }

    override suspend fun getOnGoingBroadcastVoteList(broadcastId: String): List<VoteListData> {
        val response = voteApi.getOnGoingBroadcastVoteList(broadcastId)
        val data = response.data!!
        return VoteMapper.mapperToVoteListData(data)
    }

    override suspend fun getEndBroadcastVoteList(broadcastId: String): List<VoteListData> {
        val response = voteApi.getEndBroadcastVoteList(broadcastId)
        val data = response.data!!
        return VoteMapper.mapperToVoteListData(data)
    }

    override suspend fun postVote(voteItem: VoteItem): Boolean {
        //TODO : 투표 트랜잭션 실행 후 투표 API호출로 변경할 것
        val voteRequest = VoteRequest(
            voteItem.voteSessionId,
            voteItem.candidateId,
            voteItem.transactionHash
        )
        val response = voteApi.postVote(voteRequest)

        //TODO : status = fail일 경우 예외처리할 것
        return response.status.equals("SUCCESS")
    }
}