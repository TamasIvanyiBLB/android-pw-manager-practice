package com.example.androidpracticeapp_passwordmanager.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator

class KeyGen {
    val keyGenerator: KeyGenerator? by lazy {
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_NAME)
    }

    val keyGenSpec by lazy {
        KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
    }

    val keyStore: KeyStore? = KeyStore.getInstance(KEYSTORE_NAME).apply { load(null) }

    init {

        if (keyStore?.containsAlias(KEY_ALIAS) == false) {
            keyGenerator?.init(keyGenSpec)
            keyGenerator?.generateKey()
        }
    }

    companion object {
        private const val KEYSTORE_NAME = "AndroidKeyStore"
        const val KEY_ALIAS = "PasswordManagerEncryptionKey"
    }
}