package com.ssafy.pickit.domain.repository

interface VoteRepository {
    suspend fun getVoteItems(): List<String>
}