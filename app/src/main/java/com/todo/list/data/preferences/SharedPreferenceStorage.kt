package com.todo.list.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var isFirstLoginAt: Boolean
}

@Singleton
class SharedPreferenceStorage @Inject constructor(@ApplicationContext context: Context): PreferenceStorage {
    private val preferences: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences("TASKS_PREFERENCES", Context.MODE_PRIVATE)
    }

    override var isFirstLoginAt by BooleanPreference(preferences, "FIRST_LOGIN", true)
}

class BooleanPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Boolean
): ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return  preferences.value.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.value.edit { putBoolean(name, value) }
    }
}
