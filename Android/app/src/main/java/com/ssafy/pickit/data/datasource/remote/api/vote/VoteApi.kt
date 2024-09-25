package com.ssafy.pickit.data.datasource.remote.api.vote

import com.ssafy.pickit.data.datasource.remote.response.ApiResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.VoteSessionResponse
import retrofit2.http.GET
import com.ssafy.pickit.domain.model.VoteStatus
import retrofit2.http.Path

interface VoteApi {

    @GET("vote/items")
    suspend fun getVoteItems(): List<String>

    @GET("vote/status")
    suspend fun getVoteStatus(): VoteStatus

    @GET("vote-session/ongoing")
    suspend fun getInProgressVotes(): ApiResponse<List<VoteResponse>>

    @GET("vote-session/end")
    suspend fun getCompletedVotes(): ApiResponse<List<VoteResponse>>

    @GET("votes/{voteId}")
    suspend fun getVoteDetail(@Path("voteId") voteId: String): ApiResponse<VoteSessionResponse>

    @GET("vote/{voteId}/result")
    suspend fun getVoteResult(@Path("voteId") voteId: String): VoteResultResponse

}
