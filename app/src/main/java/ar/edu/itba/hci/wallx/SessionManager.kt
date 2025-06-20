package ar.edu.itba.hci.wallx

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun loadAuthToken(): String? {
        return preferences.getString(AUTH_TOKEN, null)
    }

    fun removeAuthToken() {
        preferences.edit {
            remove(AUTH_TOKEN)
        }
    }

    fun saveAuthToken(token: String) {
        preferences.edit {
            putString(AUTH_TOKEN, token)
        }
    }

    fun getSee(): Boolean {
        return preferences.getBoolean(SEE, true)
    }

    fun toggleSee() : Boolean {
        val see = getSee()
        preferences.edit {
            putBoolean(SEE, !see)
        }
        return !see
    }

    companion object {
        const val PREFERENCES_NAME = "wallx"
        const val AUTH_TOKEN = "auth_token"
        const val SEE = "see"
    }
}
