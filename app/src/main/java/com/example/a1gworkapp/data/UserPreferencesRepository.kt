package com.example.a1gworkapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class UserCredentials(
    val city: String,
    val shop: String,
    val employeeName: String,
    val password: String
)

class UserPreferencesRepository(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    private object PreferencesKeys {
        val CITY = stringPreferencesKey("user_city")
        val SHOP = stringPreferencesKey("user_shop")
        val EMPLOYEE_NAME = stringPreferencesKey("user_employee_name")
        val PASSWORD = stringPreferencesKey("user_password")
    }

    val userCredentialsFlow: Flow<UserCredentials?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("UserPreferencesRepo", "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val city = preferences[PreferencesKeys.CITY]
            val shop = preferences[PreferencesKeys.SHOP]
            val employeeName = preferences[PreferencesKeys.EMPLOYEE_NAME]
            val password = preferences[PreferencesKeys.PASSWORD]

            if (city != null && shop != null && employeeName != null && password != null) {
                UserCredentials(city, shop, employeeName, password)
            } else {
                null
            }
        }

    suspend fun fetchInitialCredentials(): UserCredentials? {
        return userCredentialsFlow.firstOrNull()
    }

    suspend fun saveCredentials(credentials: UserCredentials) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CITY] = credentials.city
            preferences[PreferencesKeys.SHOP] = credentials.shop
            preferences[PreferencesKeys.EMPLOYEE_NAME] = credentials.employeeName
            preferences[PreferencesKeys.PASSWORD] = credentials.password
        }
        Log.d("UserPreferencesRepo", "Credentials saved successfully.")
    }

    suspend fun clearCredentials() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        Log.d("UserPreferencesRepo", "Credentials cleared.")
    }
}