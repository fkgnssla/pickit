package com.ssafy.pickit.common.blockchain

import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger


private const val INFURA_URL = ""
private const val KAT_CONTRACT_ADDRESS = ""
private const val TAG = "wallet info"

class WalletFunction {

    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, KAT_CONTRACT_ADDRESS)

    fun generateWallet() {

        // ECDSA 키 쌍 생성 난수생성기를 통해 생성한 안전한 키쌍
        val ecKeyPair: ECKeyPair = Keys.createEcKeyPair()
        // 개인 키 추출
        val privateKey: String = ecKeyPair.privateKey.toString(16)
        // 공개 키 추출
        val publicKey: String = ecKeyPair.publicKey.toString(16)
        // Credentials 객체 생성
        val credentials: Credentials = Credentials.create(privateKey)
        // wallet address
        val address = credentials.address

    }
}