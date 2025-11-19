package com.example.androidpracticeapp_passwordmanager.utils

import org.mindrot.jbcrypt.BCrypt
import java.security.KeyStore
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class PasswordUtils(val keyGen: KeyGen) {

    val cipher: Cipher? by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }

    fun generatePassword(length: Int, useSpecialCharacters: Boolean): String {
        if (length < 1) {
            return ""
        }

        val upper = ('A'..'Z')
        val lower = ('a'..'z')
        val digits = ('0'..'9')
        val specials = "!@#\\$%^&*()-_=+[]{}<>?/"

        val baseChars = upper + lower + digits
        val allChars = if (useSpecialCharacters) baseChars + specials.toList() else baseChars

        return (1..length)
            .map { allChars.random() }
            .joinToString("")
    }

    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun comparePassword(hash: String, password: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }

    fun encryptPassword(password: String): String {
        if (password.isEmpty()) {
            return ""
        }

        cipher?.init(Cipher.ENCRYPT_MODE, getSecret())
        val encrypted = cipher?.doFinal(password.toByteArray(Charsets.UTF_8))
        val encryptedBase64 = Base64.getEncoder().encodeToString(encrypted)
        val ivBase64 = Base64.getEncoder().encodeToString(cipher?.iv)

        return "$ivBase64:$encryptedBase64"
    }

    fun decryptPassword(encrypted: String): String {
        if (encrypted.isEmpty()) {
            return ""
        }

        val (ivBase64, encryptedBase64) = encrypted.split(":")
        val iv = Base64.getDecoder().decode(ivBase64)
        val spec = GCMParameterSpec(128, iv)
        cipher?.init(Cipher.DECRYPT_MODE, getSecret(), spec)

        val encryptedBytes = Base64.getDecoder().decode(encryptedBase64)
        val decrypted = cipher?.doFinal(encryptedBytes) ?: return ""

        return String(decrypted, Charsets.UTF_8)
    }

    private fun getSecret(): SecretKey {
        return (keyGen.keyStore?.getEntry(
            KeyGen.KEY_ALIAS,
            null
        ) as KeyStore.SecretKeyEntry).secretKey
    }
}