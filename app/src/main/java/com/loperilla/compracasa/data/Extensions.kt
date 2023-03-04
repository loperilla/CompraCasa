package com.loperilla.compracasa.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.loperilla.compracasa.data.Constants.PREFERENCES.PREFERENCE

object Extensions {
    val Context.mDataStore: DataStore<Preferences>
            by preferencesDataStore(name = PREFERENCE)

}