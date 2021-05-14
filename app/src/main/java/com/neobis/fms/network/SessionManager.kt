package com.neobis.fms.network

import android.content.Context
import android.content.SharedPreferences
import com.neobis.fms.R

class SessionManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    companion object {
        const val USER_TOKEN = "user_token"
        const val REFRESH_TOKEN = "refresh_token"
    }


    fun saveTokens(token: String?, refresh_token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(REFRESH_TOKEN, refresh_token)

        editor.apply()
    }

    fun saveUserToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun fetchUserToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN, null)
    }

    fun clearData() {
        prefs.edit().clear().apply()
    }
}