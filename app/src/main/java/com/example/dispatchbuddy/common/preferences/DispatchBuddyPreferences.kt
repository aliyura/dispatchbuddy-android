package com.example.dispatchbuddy.common.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.dispatchbuddy.common.preferences.PreferenceConstants.KEY_TOKEN
import javax.inject.Inject

class DispatchBuddyPreferences @Inject constructor(
    context: Context
): Preferences {

    companion object {
        const val PREFERENCES_NAME = "DISPATCH_BUDDY_PREFERENCES"
    }
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(preferences.edit()) {
            block()
            commit()
        }
    }

    override fun saveToken(token: String) {
        edit { putString(KEY_TOKEN, token) }
    }

    override fun getToken(): String {
        return preferences.getString(KEY_TOKEN, "").orEmpty()
    }

    override fun deleteTokenInfo() {
        edit { remove(KEY_TOKEN) }
    }
}