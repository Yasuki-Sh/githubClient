package com.example.githubclient.data.local

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager

class MyCipher(
    context: Context,
) {
    private val aead: Aead

    init {
        // Tink初期化（AEAD実装のみ）
        AeadConfig.register()

        // Android専用の鍵管理
        val keysetManager = AndroidKeysetManager.Builder()
            // MasterKeyで暗号化したkeysetはSharedPreferencesに保存される
            .withSharedPref(context, "keyset", "encrypted_keyset")
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            // MasterKeyはKeyStoreを使う
            // android-keystoreスキーマ：Android KeyStore を指定（固定）
            // tink_master_key：MasterKeyのalias（自由）
            .withMasterKeyUri("android-keystore://tink_master_key")
            .build()

        // プリミティブの取得
        aead = keysetManager.keysetHandle.getPrimitive(
            RegistryConfiguration.get(),
            Aead::class.java,
        )
    }

    fun encrypt(value: ByteArray, associatedData: ByteArray?): ByteArray {
        return aead.encrypt(value, associatedData)
    }

    fun decrypt(value: ByteArray, associatedData: ByteArray?): ByteArray {
        return aead.decrypt(value, associatedData)
    }
}
