package com.ssafy.pickit.data.datasource.remote.blockchain

import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManager
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger
import javax.inject.Inject


private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val DEFAULT_CONTRACT_ADDRESS = "0x8509156ac35243FABA465CF2Ebf5e21652b30F33"
private const val TAG = "wallet info"

class WalletFunctionImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val localKeyStoreManager: LocalKeyStoreManager
) : WalletFunction {
    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 기본 트랜잭션 매니저 (기본 계약 주소에서 실행되는 트랜잭션)
    private val defaultTransactionManager =
        ReadonlyTransactionManager(web3j, DEFAULT_CONTRACT_ADDRESS)

    override fun generateWallet() {
        val ecKeyPair: ECKeyPair = Keys.createEcKeyPair()
        val privateKey: String = ecKeyPair.privateKey.toString(16)
        val credentials: Credentials = Credentials.create(privateKey)
        val address = credentials.address

        setWalletInformation(privateKey, address)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val voting = Contracts_Voting_sol_Voting.load(
                    DEFAULT_CONTRACT_ADDRESS,
                    web3j,
                    credentials,
                    contractGasProvider
                )
                val totalVotes = voting.totalVotes.send()
                val candidateName = voting.candidates(BigInteger.valueOf(0)).send()

            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }

    }

    // 투표마다 다른 주소로 트랜잭션을 처리하기 위한 메서드
    fun processTransactionWithVoteContract(
        voteContractAddress: String,
        transactionData: Any
    ): Boolean {
        val transactionManager = ReadonlyTransactionManager(web3j, voteContractAddress)

        //TODO : 트랜잭션 처리 로직 구현
        return true
    }

    override fun insertUserWallet(privateKey: String) {
        val credentials: Credentials = Credentials.create(privateKey)
        val address = credentials.address
        setWalletInformation(privateKey, address)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val voting = Contracts_Voting_sol_Voting.load(
                    DEFAULT_CONTRACT_ADDRESS,
                    web3j,
                    Credentials.create(privateKey),
                    contractGasProvider
                )

            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }
    }

    private fun setWalletInformation(privateKey: String, address: String) {
        val encodedPrivateKey =
            localKeyStoreManager.encryptData(privateKey)
        val encodedAddress = localKeyStoreManager.encryptData(address)

        localPreferenceDataSource.setPrivateKey(encodedPrivateKey)
        localPreferenceDataSource.setWalletAddress(encodedAddress)
    }
}