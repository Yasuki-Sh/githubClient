package com.example.githubclient.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun securePreferencesDataStore(
    cipher: MyCipher,
    name: String,
) = object : ReadOnlyProperty<Context, DataStore<SecurePreferences>> {

    private val Context.original by preferencesDataStore(name)

    override fun getValue(
        thisRef: Context,
        property: KProperty<*>,
    ): DataStore<SecurePreferences> {
        val original = with(thisRef) { original }
        return object : DataStore<SecurePreferences> {
            override val data: Flow<SecurePreferences> =
                original.data.map { SecurePreferences(cipher, it) }

            override suspend fun updateData(transform: suspend (SecurePreferences) -> SecurePreferences): SecurePreferences {
                val transformed = original.updateData {
                    transform(SecurePreferences(cipher, it)).toPreferences()
                }
                return SecurePreferences(cipher, transformed)
            }
        }
    }
}

suspend fun DataStore<SecurePreferences>.edit(
    transform: suspend (MutableSecurePreferences) -> Unit
): SecurePreferences {
    return this.updateData {
        it.toMutable().apply {
            transform(this)
        }
    }
}