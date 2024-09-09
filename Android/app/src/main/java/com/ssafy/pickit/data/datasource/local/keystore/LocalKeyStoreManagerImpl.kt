package com.ssafy.pickit.data.datasource.local.keystore

class LocalKeyStoreManagerImpl(private val keyStoreHelper: KeyStoreManager) : LocalKeyStoreManager {

    override fun encryptData(data: ByteArray): ByteArray {
        return keyStoreHelper.encryptData(data)
    }

    override fun decryptData(data: ByteArray): ByteArray {
        return keyStoreHelper.decryptData(data)
    }
}
