package com.ssafy.pickit.data.di

import com.ssafy.pickit.domain.repository.AuthRepository
import com.ssafy.pickit.domain.repository.VoteRepository
import com.ssafy.pickit.domain.repository.WalletRepository
import com.ssafy.pickit.domain.usecase.auth.LoginUseCase
import com.ssafy.pickit.domain.usecase.auth.RegisterUseCase
import com.ssafy.pickit.domain.usecase.vote.EndBroadcastVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.EndVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingBroadcastVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.OngoingVoteListUseCase
import com.ssafy.pickit.domain.usecase.vote.VoteUseCase
import com.ssafy.pickit.domain.usecase.wallet.GenerateWalletUseCase
import com.ssafy.pickit.domain.usecase.wallet.InsertWalletUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase =
        RegisterUseCase(authRepository)

    @Singleton
    @Provides
    fun provideGenerateWalletUseCase(walletRepository: WalletRepository): GenerateWalletUseCase =
        GenerateWalletUseCase(walletRepository)

    @Singleton
    @Provides
    fun provideInsertWalletUseCase(walletRepository: WalletRepository): InsertWalletUseCase =
        InsertWalletUseCase(walletRepository)

    @Provides
    @Singleton
    fun provideEndBroadcastVoteListUseCase(voteRepository: VoteRepository): EndBroadcastVoteListUseCase {
        return EndBroadcastVoteListUseCase(voteRepository)
    }

    @Provides
    @Singleton
    fun provideEndVoteListUseCase(voteRepository: VoteRepository): EndVoteListUseCase {
        return EndVoteListUseCase(voteRepository)
    }

    @Provides
    @Singleton
    fun provideOngoingBroadcastVoteListUseCase(voteRepository: VoteRepository): OngoingBroadcastVoteListUseCase {
        return OngoingBroadcastVoteListUseCase(voteRepository)
    }

    @Provides
    @Singleton
    fun provideOngoingVoteListUseCase(voteRepository: VoteRepository): OngoingVoteListUseCase {
        return OngoingVoteListUseCase(voteRepository)
    }

    @Provides
    @Singleton
    fun provideVoteUseCase(voteRepository: VoteRepository): VoteUseCase {
        return VoteUseCase(voteRepository)
    }

//    @Provides
//    @Singleton
//    fun provideVoteDetailUseCase(voteRepository: VoteRepository): VoteDetailUseCase {
//        return VoteUseCase(voteRepository)
//    }

}