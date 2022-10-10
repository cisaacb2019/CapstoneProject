package com.cb.week5homeworkfinal.DataBase.PrefsStore

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException




private const val STORE_NAME = "user_preferences"

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
    }.map { it[PreferencesKeys.NIGHT_MODE_KEY]  ?: false }

    override suspend fun toogleinternetMode() {
        context.dataStore.edit {
            it[PreferencesKeys.NIGHT_MODE_KEY] = !(it[PreferencesKeys.NIGHT_MODE_KEY] ?: false )
        }
    }

    private object PreferencesKeys{
        val NIGHT_MODE_KEY = booleanPreferencesKey("dark_theme_enabled")
    }

}