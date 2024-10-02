package com.ssafy.pickit.data.repositoryImpl

import android.util.Log
import com.ssafy.pickit.data.datasource.remote.api.vote.VoteApi
import com.ssafy.pickit.data.datasource.remote.blockchain.TransactionState
import com.ssafy.pickit.data.datasource.remote.blockchain.WalletFunction
import com.ssafy.pickit.data.datasource.remote.request.vote.VoteRequest
import com.ssafy.pickit.data.mapper.VoteMapper
import com.ssafy.pickit.data.mapper.VoteMapper.mapperToVoteResultData
import com.ssafy.pickit.data.mapper.VoteMapper.mapperToVoteSessionData
import com.ssafy.pickit.domain.entity.VoteItem
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.entity.VoteResultData
import com.ssafy.pickit.domain.entity.VoteSessionData
import com.ssafy.pickit.domain.repository.VoteRepository
import javax.inject.Inject

//TODO : 실패시 예외 response 처리할 것
class VoteRepositoryImpl @Inject constructor(
    private val voteApi: VoteApi,
    private val walletFunction: WalletFunction
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
        val transactionResponse =
            walletFunction.vote(voteItem.contractAddress, voteItem.candidateId)

        if (transactionResponse.status == TransactionState.Success && transactionResponse.transactionHash != null) {
            val voteRequest = VoteRequest(
                voteItem.contractAddress,
                voteItem.candidateId,
                transactionResponse.transactionHash
            )
            val response = voteApi.postVote(voteRequest)
            return response.status.equals("SUCCESS")
        }
        Log.d("testtttt", transactionResponse.message.toString())
        return false
    }

    override suspend fun getVoteDetail(voteId: String): VoteSessionData {
        val response = voteApi.getVoteDetail(voteId)
        val voteSessionResponse = response.data ?: throw Exception("Failed to retrieve vote detail")


        return mapperToVoteSessionData(voteSessionResponse)
    }


    override suspend fun getVoteResult(voteId: String): VoteResultData {
        val response = voteApi.getVoteResult(voteId)
        val voteResultResponse = response.data ?: throw Exception("Failed to retrieve vote result")

        return mapperToVoteResultData(voteResultResponse)
    }
}

