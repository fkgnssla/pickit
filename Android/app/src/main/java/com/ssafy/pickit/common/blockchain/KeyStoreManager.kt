package com.ssafy.pickit.common.blockchain

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.spec.RSAKeyGenParameterSpec
import java.util.Calendar

class KeyStoreManager(private val context : Context) {
    private val KEYSTORE_PROVIDER = "AndroidKeyStore"
    private val ASYMMETRIC_KEY_ALIAS = "my_key_alias"

    init {
        // 안드로이드 키스토어에 키 생성
        createAsymmetricKeyPair()
        Log.d("key store info", "key store 생성 ")
    }

    private fun createAsymmetricKeyPair() {
        if (!isAsymmetricKeyPairExist()) {

            Log.d("key store info", "key store 키쌍 없어서 생성 ")

            // KeyGenParameterSpec.Builder를 사용하여 RSA 키 쌍 생성
            val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER)

            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                ASYMMETRIC_KEY_ALIAS,  // 별칭
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT  // 키의 용도 (암호화 및 복호화)
            )
                .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4))  // RSA 2048비트 키 생성
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)  // 사용할 해시 알고리즘 지정
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)  // 암호화 패딩 방식 설정
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)  // 블록 모드 설정
                .setKeySize(2048)  // 키 크기 지정
                .setUserAuthenticationRequired(false)  // 사용자 인증 필요 여부
                .setKeyValidityStart(Calendar.getInstance().time)  // 키 유효 기간 시작
                .setKeyValidityEnd(Calendar.getInstance().apply { add(Calendar.YEAR, 30) }.time)  // 키 유효 기간 끝 (30년)
                .build()

            keyPairGenerator.initialize(keyGenParameterSpec)  // 설정 초기화
            keyPairGenerator.generateKeyPair()  // 키 쌍 생성
        }
    }


    private fun isAsymmetricKeyPairExist(): Boolean {

        Log.d("key store info", "key store 키쌍 확인 ")

        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
        keyStore.load(null)
        val privateKey = keyStore.getKey(ASYMMETRIC_KEY_ALIAS, null)
        return privateKey != null
    }


}