package com.github.gotify

import android.content.Context
import android.content.SharedPreferences
import com.github.gotify.client.model.User

internal class Settings(context: Context) {
    private val sharedPreferences: SharedPreferences
    var url: String
        get() = sharedPreferences.getString("url", "")!!
        set(value) = sharedPreferences.edit().putString("url", value).apply()
    var signUrl: String
        get() = sharedPreferences.getString("signUrl", "")!!
        set(value) = sharedPreferences.edit().putString("signUrl", value).apply()
    var isAuto: Boolean
        get() = sharedPreferences.getBoolean("isAuto", false)
        set(value) = sharedPreferences.edit().putBoolean("isAuto", value).apply()
    var token: String?
        get() = sharedPreferences.getString("token", null)
        set(value) = sharedPreferences.edit().putString("token", value).apply()
    var user: User? = null
        get() {
            val username = sharedPreferences.getString("username", null)
            val admin = sharedPreferences.getBoolean("admin", false)
            return if (username != null) {
                User().name(username).admin(admin)
            } else {
                User().name("UNKNOWN").admin(false)
            }
        }
        private set
    var serverVersion: String
        get() = sharedPreferences.getString("version", "UNKNOWN")!!
        set(value) = sharedPreferences.edit().putString("version", value).apply()
    var cert: String?
        get() = sharedPreferences.getString("cert", null)
        set(value) = sharedPreferences.edit().putString("cert", value).apply()
    var validateSSL: Boolean
        get() = sharedPreferences.getBoolean("validateSSL", true)
        set(value) = sharedPreferences.edit().putBoolean("validateSSL", value).apply()

    init {
        sharedPreferences = context.getSharedPreferences("gotify", Context.MODE_PRIVATE)
    }

    fun tokenExists(): Boolean = !token.isNullOrEmpty()

    fun clear() {
        url = ""
        signUrl = ""
        token = null
        validateSSL = true
        cert = null
    }

    fun setUser(name: String?, admin: Boolean) {
        sharedPreferences.edit().putString("username", name).putBoolean("admin", admin).apply()
    }

    fun sslSettings(): SSLSettings {
        return SSLSettings(validateSSL, cert)
    }
}
