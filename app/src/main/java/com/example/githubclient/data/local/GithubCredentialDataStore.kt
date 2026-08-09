package com.example.githubclient.data.local

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
class GithubCredentialDataStore private constructor(context: Context) {

    private val cipher = MyCipher(context.applicationContext)
    private val Context.dataStore by securePreferencesDataStore(cipher, "settings")
    private val store = context.applicationContext.dataStore

    companion object {
        @Volatile
        private var INSTANCE: GithubCredentialDataStore? = null

        fun getInstance(context: Context): GithubCredentialDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: GithubCredentialDataStore(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
    suspend fun saveCredentials(owner: String, token: String) {
            store.edit {
                it[stringPreferencesKey("owner")] = owner
                it[stringPreferencesKey("token")] = token
            }
    }
    suspend fun getCredentials(): UserCredentials {
        val preferences = store.data.first()
        return UserCredentials(
            preferences[stringPreferencesKey("owner")] ?: "",
            preferences[stringPreferencesKey("token")] ?: ""
        )
    }
}

data class UserCredentials(
    val owner: String,
    val token: String
)