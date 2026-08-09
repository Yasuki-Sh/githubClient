package com.example.githubclient.dataStore

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.githubclient.data.local.GithubCredentialDataStore
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DataStoreTest {

    val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun saveAndGetCredentials() = runBlocking {
        val dataStore = GithubCredentialDataStore.getInstance(context)
        dataStore.saveCredentials("octocat", "ghp_xxx")
        val credentials = dataStore.getCredentials()
        assertEquals("octocat", credentials.owner)
        assertEquals("ghp_xxx", credentials.token)
    }
}