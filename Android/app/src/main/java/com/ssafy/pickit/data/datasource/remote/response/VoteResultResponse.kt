package com.ssafy.pickit.data.datasource.remote.response

data class VoteResultResponse(
    val voteSession: VoteSessionResponse,
    val results: List<CandidateResult>
)

data class CandidateResult(
    val candidateId: String,
    val candidateName: String,
    val voteCount: Long
)
