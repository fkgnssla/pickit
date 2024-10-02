package com.ssafy.pickit.data.mapper


import com.ssafy.pickit.data.datasource.remote.response.vote.CandidateResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.CandidateResult
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteListDataResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteResultResponse
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteSessionResponse
import com.ssafy.pickit.domain.entity.CandidateData
import com.ssafy.pickit.domain.entity.CandidateResultData
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.domain.entity.VoteResultData
import com.ssafy.pickit.domain.entity.VoteSessionData

object VoteMapper {
    fun mapperToVoteListData(voteListDataResponse: List<VoteListDataResponse>): List<VoteListData> {
        return voteListDataResponse.map { response ->
            mapSingleVoteData(response)
        }
    }

    // 개별 VoteListDataResponse -> VoteListData 변환
    private fun mapSingleVoteData(response: VoteListDataResponse): VoteListData {
        return VoteListData(
            id = response.id,
            title = response.title,
            thumbnail = response.thumbnail,
            startDate = response.startDate,
            endDate = response.endDate,
            isVote = response.isVote
        )
    }

    fun mapperToVoteSessionData(response: VoteSessionResponse): VoteSessionData {
        return VoteSessionData(
            id = response.id,
            title = response.title,
            description = response.description,
            thumbnail = response.thumbnail,
            candidates = response.candidates.map { mapCandidate(it) },
            startDate = response.startDate,
            endDate = response.endDate
        )
    }


    private fun mapCandidate(candidate: CandidateResponse): CandidateData {
        return CandidateData(
            name = candidate.name,
            profileImg = candidate.profileImg,
            voteCnt = candidate.voteCnt
        )
    }

    fun mapperToVoteResultData(response: VoteResultResponse): VoteResultData {
        return VoteResultData(
            results = response.results.map { mapCandidateResult(it) }
        )
    }

    private fun mapCandidateResult(result: CandidateResult): CandidateResultData {
        return CandidateResultData(
            candidateId = result.candidateId,
            candidateName = result.candidateName,
            voteCount = result.voteCount,
            profileImage=result.profileImage,
            isVote=result.isVote
        )
    }
}