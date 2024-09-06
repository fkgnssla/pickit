package com.ssafy.pickit.data.datasource.remote.api.vote


import retrofit2.http.GET


interface VoteApi {

    @GET("vote/items")
    suspend fun getVoteItems(): List<String>
}