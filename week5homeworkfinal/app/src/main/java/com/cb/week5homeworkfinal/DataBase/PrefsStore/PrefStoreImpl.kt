package com.cb.week5homeworkfinal.DataBase.PrefsStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException



private const val STORE_NAME = "user_preferences"
//tried to adjust to datastore but was unable to get it to save reverted to dark mode//
private val Context.dataStore by preferencesDataStore(
    name = STORE_NAME,
    produceMigrations = {context ->
        listOf(SharedPreferencesMigration(context, STORE_NAME))
    }
)

class PrefsStoreImpl (private val context: Context) : PrefsStore {

//    private var appContext = context.applicationContext

    override fun isInternetMode() = context.dataStore.data.catch { exception ->
        if (exception is IOException){
            emit(emptyPreferences())
        }else{
            throw exception
        }
    }.map { it[PreferencesKeys.Internet_Mode_Key]  ?: false }

    override suspend fun toogleinternetMode() {
        context.dataStore.edit {
            it[PreferencesKeys.Internet_Mode_Key] = !(it[PreferencesKeys.Internet_Mode_Key] ?: false)
        }
    }

    private object PreferencesKeys{
        val Internet_Mode_Key = booleanPreferencesKey("dark_theme_enabled")
    }

}