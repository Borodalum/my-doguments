package com.doguments.my.repository.util

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class PasswordEncryptionUtil(private val secretKey: String) {
    private val algorithm = "AES"
    private val transformation = "AES/ECB/PKCS5Padding"

    private fun getSecretKeySpec(): SecretKeySpec {
        // Ключ должен быть ровно 16 байт для AES-128.
        val keyBytes = secretKey.toByteArray(Charsets.UTF_8)
        return SecretKeySpec(keyBytes, algorithm)
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec())
        val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec())
        val decodedBytes = Base64.getDecoder().decode(encrypted)
        return String(cipher.doFinal(decodedBytes), Charsets.UTF_8)
    }
}