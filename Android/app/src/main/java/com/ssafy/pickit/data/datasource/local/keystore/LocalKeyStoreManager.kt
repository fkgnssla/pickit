package com.ssafy.pickit.data.datasource.local.keystore

interface LocalKeyStoreManager {
    fun encryptData(data: ByteArray): ByteArray
    fun decryptData(data: ByteArray): ByteArray
}
