package com.test.util

import android.content.Context
import android.widget.EditText
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.test.util.DataStoreKey.NAME
import kotlinx.coroutines.flow.first

private val Context.suggestTo: DataStore<Preferences> by preferencesDataStore(name = "suggestTo")
private val Context.suggestFrom: DataStore<Preferences> by preferencesDataStore(name = "suggestFrom")

suspend fun EditText.saveToSuggest(context: Context){
    context.suggestTo.edit {
        it[NAME] = text.toString()
    }
}

suspend fun EditText.saveFromSuggest(context: Context){
    context.suggestFrom.edit {
        it[NAME] = text.toString()
    }
}

suspend fun EditText.getToSuggest(context: Context) = context.suggestTo.data.first()[NAME]
suspend fun EditText.getFromSuggest(context: Context) = context.suggestFrom.data.first()[NAME]

private object DataStoreKey {
    val NAME = stringPreferencesKey("town")
}