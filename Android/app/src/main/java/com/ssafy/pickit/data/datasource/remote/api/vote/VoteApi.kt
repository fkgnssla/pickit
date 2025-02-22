package com.ssafy.pickit.data.datasource.remote.api.vote

import com.ssafy.pickit.data.datasource.remote.request.vote.VoteRequest
import com.ssafy.pickit.data.datasource.remote.response.ResponseWrapper
import com.ssafy.pickit.data.datasource.remote.response.vote.CandidateResult
import com.ssafy.pickit.data.datasource.remote.response.vote.PopularVoteListDataResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteListDataResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteSessionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VoteApi {

    @GET("vote-session/ongoing")
    suspend fun getOnGoingVoteList(): ResponseWrapper<List<VoteListDataResponse>>

    @GET("vote-session/end")
    suspend fun getEndVoteList(): ResponseWrapper<List<VoteListDataResponse>>

    @GET("vote-session/popular")
    suspend fun getPopularVoteList(): ResponseWrapper<List<PopularVoteListDataResponse>>

    @GET("vote-session/ongoing/{broadcastId}")
    suspend fun getOnGoingBroadcastVoteList(@Path("broadcastId") broadcastId: String): ResponseWrapper<List<VoteListDataResponse>>

    @GET("vote-session/end/{broadcastId}")
    suspend fun getEndBroadcastVoteList(@Path("broadcastId") broadcastId: String): ResponseWrapper<List<VoteListDataResponse>>

    @POST("vote")
    suspend fun postVote(
        @Body request: VoteRequest
    ): ResponseWrapper<Unit>

    @GET("vote-session/{voteId}")
    suspend fun getVoteDetail(@Path("voteId") voteId: String): ResponseWrapper<VoteSessionResponse>

    @GET("vote-session/results/{voteId}")
    suspend fun getVoteResult(@Path("voteId") voteId: String): ResponseWrapper<List<CandidateResult>>

    @GET("vote-session/my/ongoing")
    suspend fun getOnGoingMyVoteList(): ResponseWrapper<List<VoteListDataResponse>>

    @GET("vote-session/my/end")
    suspend fun getEndMyVoteList(): ResponseWrapper<List<VoteListDataResponse>>

    @GET("vote-session/search")
    suspend fun getSearchVoteList(@Query("keyword") keyword : String) : ResponseWrapper<List<VoteListDataResponse>>

}