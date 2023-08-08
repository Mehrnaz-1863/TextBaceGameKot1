package com.example.textbacegamekot1.data_store

import android.content.Context

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.datastore.preferences.preferencesDataStore


class DataStoreViewModel(application: Application) : AndroidViewModel(application) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

    val dataStore: DataStore<Preferences> = application.dataStore
}
