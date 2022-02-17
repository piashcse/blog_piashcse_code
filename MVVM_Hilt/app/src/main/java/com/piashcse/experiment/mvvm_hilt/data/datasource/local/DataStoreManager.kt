package com.piashcse.experiment.mvvm_hilt.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.piashcse.experiment.mvvm_hilt.utils.fromPrettyJson
import com.piashcse.experiment.mvvm_hilt.utils.fromPrettyJsonList
import com.piashcse.experiment.mvvm_hilt.utils.toPrettyJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreManager(var context: Context) {
    // Create the dataStore and give it a name same as shared preferences
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    val dataStore: DataStore<Preferences> = context.dataStore

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
    }

    // Store user data
    // refer to the data store and using edit


    // store data as object
    suspend inline fun <reified T> storeObjectAsJson(key: Preferences.Key<String>, value: T) {
        dataStore.edit { preferences ->
            if (value != null) {
                preferences[key] = value.toPrettyJson()
            }
        }
    }

    // save primitives data
    suspend fun storeStringData(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun storeIntData(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun storeDoubleData(key: Preferences.Key<Double>, value: Double) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun storeDoubleData(key: Preferences.Key<Float>, value: Float) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun storeLongData(key: Preferences.Key<Long>, value: Long) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun storeBooleanData(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    // Create a flow to retrieve data from the preferences
    // flow comes from the kotlin coroutine

    // get data as object
    inline fun <reified T: Any> getObjectData(key: Preferences.Key<String>): Flow<T> =
        dataStore.data.catch {exception->
            // dataStore.data throws an IOException if it can't read the data
            if (exception is IOException) { // 2
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            (it[key] ?: "").fromPrettyJson()
        }

    // get data as object
    inline fun <reified T: Any> getObjectArray(key: Preferences.Key<String>): Flow<List<T>> =
        dataStore.data.catch {exception->
            // dataStore.data throws an IOException if it can't read the data
            if (exception is IOException) { // 2
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            (it[key] ?: "").fromPrettyJsonList()
        }

    // get primitives data
    fun getStringData(key: Preferences.Key<String>): Flow<String> = dataStore.data.catch {exception->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: ""
    }

    fun getIntData(key: Preferences.Key<Int>): Flow<Int> = dataStore.data.catch {exception->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: 0
    }

    fun getDoubleData(key: Preferences.Key<Double>): Flow<Double> = dataStore.data.catch {exception->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: 0.0
    }

    fun getFloatData(key: Preferences.Key<Float>): Flow<Float> = dataStore.data.catch {exception->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: 0.0f
    }

    fun getLongData(key: Preferences.Key<Long>): Flow<Long> = dataStore.data.catch {exception->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: 0
    }

    fun getBooleanData(key: Preferences.Key<Boolean>): Flow<Boolean> = dataStore.data.catch {exception->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: false
    }


   /* private fun <T> serializeData(data: T): String {
        return Gson().toJson(data)
    }*/
    inline fun <reified T : Any> T.serializeData() : String = Gson().toJson(this, T::class.java)


    inline fun <reified T> deSerializeData(data: String): T {
        return Gson().fromJson(data, T::class.java)
    }
}