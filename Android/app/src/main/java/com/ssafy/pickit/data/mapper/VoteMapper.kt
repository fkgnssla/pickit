package com.ssafy.pickit.data.mapper

import com.ssafy.pickit.data.datasource.remote.response.vote.VoteListDataResponse
import com.ssafy.pickit.domain.entity.VoteListData

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
}