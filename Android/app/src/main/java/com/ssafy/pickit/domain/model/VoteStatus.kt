package com.ssafy.pickit.domain.model

sealed class VoteStatus {
    object InProgress : VoteStatus()
    object Completed : VoteStatus()
    object Error : VoteStatus()
}
