package com.gdsc.linkor.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataStore @Inject constructor(private val context: Context) {
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )
    companion object{
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")

    }

    fun getEmail(): Flow<String?> = context.dataStore.data
        .map{preferences->
            preferences[USER_EMAIL_KEY]?:""
        }

    val savedEmail: Flow<String?> = context.dataStore.data
        .map{preferences->
            preferences[USER_EMAIL_KEY]?:""
        }


    suspend fun saveEmail(email:String){
        context.dataStore.edit{ preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    fun getName(): Flow<String?> = context.dataStore.data
        .map{preferences->
            preferences[USER_NAME]?:""
        }

    suspend fun saveName(name:String){
        context.dataStore.edit{ preferences ->
            preferences[USER_NAME] = name
        }
    }


}

