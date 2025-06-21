package com.example.crocha

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, phone: String, email: String) {
        prefs.edit()
            .putString("username", username)
            .putString("phone", phone)
            .putString("email", email)
            .apply()
    }

    fun getUser(): User? {
        val username = prefs.getString("username", null)
        val phone = prefs.getString("phone", null)
        val email = prefs.getString("email", null)
        return if (username != null && phone != null && email != null) {
            User(username, phone, email)
        } else {
            null
        }
    }

    fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}

data class User(val username: String, val phone: String, val email: String) 