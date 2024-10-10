package com.ssafy.pickit.data.datasource.local.keystore

import android.util.Base64
import javax.inject.Inject

class LocalKeyStoreManagerImpl @Inject constructor(private val keyStoreHelper: KeyStoreManager) :
    LocalKeyStoreManager {

    override fun encryptData(data: String): String {
        return encodeToString(keyStoreHelper.encryptData(data.toByteArray()))
    }

    override fun decryptData(data: String): String {
        return String(keyStoreHelper.decryptData(decode(data)))
    }

    private fun encodeToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun decode(string: String): ByteArray {
        return Base64.decode(string, Base64.DEFAULT)
    }
}
