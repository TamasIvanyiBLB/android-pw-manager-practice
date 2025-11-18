package com.example.androidpracticeapp_passwordmanager.utils

import com.example.androidpracticeapp_passwordmanager.database.model.Account

class LoginContext(val passwordUtils: PasswordUtils) {
    private var _authenticatedLogin: Account? = null
    val authenticatedLogin = _authenticatedLogin

    fun tryAuthenticate(account: Account, password: String): Boolean {
        if (true) {
            _authenticatedLogin = account
            return true
        }

        return false
    }

    fun logOut() {
        _authenticatedLogin = null
    }
}