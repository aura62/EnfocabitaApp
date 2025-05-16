package com.aura.enfocabita.domain.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Pbkdf2PasswordHasher(
    private val iterations: Int = 10000,
    private val saltLength: Int = 16,
    private val keyLength: Int = 256
) : PasswordHasher {

    private val random = SecureRandom()

    override fun hash(password: String): String {
        // 1) Generar salt
        val salt = ByteArray(saltLength).also { random.nextBytes(it) }

        // 2) Derivar clave
        val spec = PBEKeySpec(password.toCharArray(), salt, iterations, keyLength)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = skf.generateSecret(spec).encoded

        // 3) Empaquetar todo en una string: "iteraciones:base64(salt):base64(hash)"
        val b64 = Base64.NO_WRAP or Base64.NO_PADDING
        return listOf(
            iterations.toString(),
            Base64.encodeToString(salt, b64),
            Base64.encodeToString(hash, b64)
        ).joinToString(":")
    }

    override fun verify(password: String, stored: String): Boolean {
        val parts = stored.split(':')
        if (parts.size != 3) return false
        val (iterStr, saltB64, hashB64) = parts
        val iters = iterStr.toIntOrNull() ?: return false
        val salt = Base64.decode(saltB64, Base64.NO_WRAP or Base64.NO_PADDING)
        val expectedHash = Base64.decode(hashB64, Base64.NO_WRAP or Base64.NO_PADDING)

        val spec = PBEKeySpec(password.toCharArray(), salt, iters, expectedHash.size * 8)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val actualHash = skf.generateSecret(spec).encoded

        // Comparación en “constant time” para evitar timing attacks
        if (actualHash.size != expectedHash.size) return false
        var diff = 0
        for (i in actualHash.indices) {
            diff = diff or (actualHash[i].toInt() xor expectedHash[i].toInt())
        }
        return diff == 0
    }
}