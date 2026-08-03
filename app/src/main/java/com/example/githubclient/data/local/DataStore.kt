package com.example.githubclient.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStore(private val context: Context) {
    suspend fun saveCredentials(owner: String, token: String) {
            context.dataStore.edit {
                it[stringPreferencesKey("owner")] = owner
                it[stringPreferencesKey("token")] = token
            }
    }
    suspend fun getCredentials(): UserCredentials {
        val preferences = context.dataStore.data.first()
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