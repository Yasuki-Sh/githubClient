package com.example.githubclient.data.local

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import java.util.Base64
import java.security.SecureRandom

open class SecurePreferences(
    private val cipher: MyCipher,
    private val preferences: Preferences,
) {
    companion object {
        // Associated Dataを使用した暗号化用の定数
        const val ASSOCIATED_DATA_LENGTH = 16
    }

    operator fun contains(key: Preferences.Key<String>): Boolean = key in preferences

    operator fun get(key: Preferences.Key<String>): String? {
        val data = preferences[key] ?: return null
        val decoded = Base64.getDecoder().decode(data)

        // 先頭16バイトが associated data、残りが暗号化データ
        require(decoded.size > ASSOCIATED_DATA_LENGTH)
        val associatedData = decoded.sliceArray(0 until ASSOCIATED_DATA_LENGTH)
        val encryptedData = decoded.sliceArray(ASSOCIATED_DATA_LENGTH until decoded.size)

        val decryptedValue = cipher.decrypt(encryptedData, associatedData)
        return String(decryptedValue, Charsets.UTF_8)
    }

    fun toMutable() = MutableSecurePreferences(
        cipher = cipher,
        preferences = preferences.toMutablePreferences(),
    )

    fun toPreferences() = preferences.toPreferences()
}

class MutableSecurePreferences(
    private val cipher: MyCipher,
    private val preferences: MutablePreferences,
) : SecurePreferences(cipher, preferences) {

    private val random = SecureRandom()

    operator fun set(key: Preferences.Key<String>, value: String) {
        // ランダムなassociated dataを生成（16バイト）
        val associatedData = ByteArray(ASSOCIATED_DATA_LENGTH)
        random.nextBytes(associatedData)

        val plaintext = value.toByteArray(Charsets.UTF_8)

        // associated dataを使って暗号化
        val encryptedValue = cipher.encrypt(plaintext, associatedData)

        // 先頭16バイトにassociated data、その後に暗号化データを配置
        val combinedData = associatedData + encryptedValue
        val encodedData = Base64.getEncoder().encodeToString(combinedData)
        preferences[key] = encodedData
    }

    fun clear() {
        preferences.clear()
    }
}
