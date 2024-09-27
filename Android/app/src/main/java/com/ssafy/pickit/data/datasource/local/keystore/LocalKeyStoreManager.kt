package com.ssafy.pickit.data.datasource.local.keystore

interface LocalKeyStoreManager {
    fun encryptData(data: String): String
    fun decryptData(data: String): String
}
